package edu.utsa.cs3443.caltracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import edu.utsa.cs3443.caltracker.model.FoodEntry;
import edu.utsa.cs3443.caltracker.model.LogAdapter;
import edu.utsa.cs3443.caltracker.model.LogEntry;
import edu.utsa.cs3443.caltracker.model.LogRepository;

public class LogActivity extends AppCompatActivity {

    private File csvFile;

    private TextView tvDate;
    private TextView tvCaloriesConsumed, tvCaloriesBurned, tvCaloriesRemaining;
    private TextView tvFat, tvCarbs, tvFiber, tvProtein;
    private RecyclerView rvLogEntries;
    private BottomNavigationView bottomNav;

    private LogRepository repo;
    private LogAdapter adapter;
    private LocalDate currentDate;
    private FloatingActionButton addNewLog;
    private final List<LogEntry> allEntries = new ArrayList<>();


    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("M/d/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // --- find views ---
        tvDate = findViewById(R.id.tvDate);
        tvCaloriesConsumed = findViewById(R.id.tvCaloriesConsumed);
        tvCaloriesBurned = findViewById(R.id.tvCaloriesBurned);
        tvCaloriesRemaining = findViewById(R.id.tvCaloriesRemaining);
        tvFat = findViewById(R.id.tvFat);
        tvCarbs = findViewById(R.id.tvCarbs);
        tvFiber = findViewById(R.id.tvFiber);
        tvProtein = findViewById(R.id.tvProtein);
        rvLogEntries = findViewById(R.id.rvLogEntries);
        bottomNav = findViewById(R.id.bottomNav);
        addNewLog = findViewById(R.id.add_log);

        // --- init repo & adapter ---
        repo = new LogRepository();
        adapter = new LogAdapter((entry, anchorView) -> showEntryOptions(entry, anchorView));
        rvLogEntries.setLayoutManager(new LinearLayoutManager(this));
        rvLogEntries.setAdapter(adapter);

        // --- setup date picker on the header ---
        currentDate = LocalDate.now();
        updateDateHeader();
        tvDate.setOnClickListener(v -> showDatePicker(tvDate));

        // --- bottom navigation ---
        bottomNav.setSelectedItemId(R.id.nav_log);
        bottomNav.setOnNavigationItemSelectedListener(this::onNavItemSelected);


        //wire clicks
        addNewLog.setOnClickListener(v -> showAddLogDialog());

        csvFile = new File(getFilesDir(), "entries.csv");
        seedCsvFromAssetsIfNeeded("entries.csv"); // copies from assets once, or creates header
        loadEntries();                             // now reads from INTERNAL file, not assets

        refreshData();


    }

    private void showAddLogDialog() {
        EditText etDesc    = new EditText(this);
        EditText etCal     = new EditText(this);
        EditText etFat     = new EditText(this);
        EditText etCarbs   = new EditText(this);
        EditText etFiber   = new EditText(this);
        EditText etProtein = new EditText(this);

        etDesc.setHint("Description");
        etCal.setHint("Calories (int; negative ok)");
        etFat.setHint("Fat (g, int)");
        etCarbs.setHint("Carbs (g, int)");
        etFiber.setHint("Fiber (g, int)");
        etProtein.setHint("Protein (g, int)");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);
        layout.addView(etDesc);
        layout.addView(etCal);
        layout.addView(etFat);
        layout.addView(etCarbs);
        layout.addView(etFiber);
        layout.addView(etProtein);

        new AlertDialog.Builder(this)
                .setTitle("Add Log")
                .setView(layout)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (d, wBtn) -> {
                    String desc = String.valueOf(etDesc.getText()).trim().replace(",", " ");
                    String sCal = String.valueOf(etCal.getText()).trim();
                    String sFat = String.valueOf(etFat.getText()).trim();
                    String sCarbs = String.valueOf(etCarbs.getText()).trim();
                    String sFiber = String.valueOf(etFiber.getText()).trim();
                    String sProtein = String.valueOf(etProtein.getText()).trim();

                    try {
                        int calories = sCal.isEmpty() ? 0 : Integer.parseInt(sCal);
                        int fat      = sFat.isEmpty() ? 0 : Integer.parseInt(sFat);
                        int carbs    = sCarbs.isEmpty() ? 0 : Integer.parseInt(sCarbs);
                        int fiber    = sFiber.isEmpty() ? 0 : Integer.parseInt(sFiber);
                        int protein  = sProtein.isEmpty() ? 0 : Integer.parseInt(sProtein);

                        LogEntry e = new LogEntry(
                                System.currentTimeMillis(),
                                currentDate,
                                desc, calories, fat, carbs, fiber, protein
                        );

                        if (appendToCsv(e)) {
                            repo.addEntry(e);
                            refreshData();
                        } else {
                            Toast.makeText(this, "Failed to save entry.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (NumberFormatException nfe) {
                        Toast.makeText(this, "Please enter integers only.", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    private void loadEntries() {
        allEntries.clear();
        // (optional) avoid double-adding if loadEntries() somehow runs twice:
        // load into a temp list, then add to repo only if that id isn’t present.

        try (FileInputStream fis = new FileInputStream(csvFile);
             Scanner sc = new Scanner(fis)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.regionMatches(true, 0, "id,", 0, 3)) continue;

                try (Scanner row = new Scanner(line)) {
                    row.useDelimiter("\\s*,\\s*");

                    long id        = row.nextLong();
                    LocalDate date = LocalDate.parse(row.next());
                    String desc    = row.next();
                    int calories   = row.nextInt();
                    int fat        = row.nextInt();
                    int carbs      = row.nextInt();
                    int fiber      = row.nextInt();
                    int protein    = row.nextInt();

                    LogEntry e = new LogEntry(id, date, desc, calories, fat, carbs, fiber, protein);
                    allEntries.add(e);

                    // push into repo so UI sees it
                    // (optional: skip if same id already in repo to prevent duplicates)
                    repo.addEntry(e);
                }
            }
        } catch (Exception e) {
            android.util.Log.e("LogActivity", "Failed to load entries", e);
        }
    }

    private void seedCsvFromAssetsIfNeeded(String assetName) {
        if (csvFile.exists() && csvFile.length() > 0) return;

        try (InputStream in = getAssets().open(assetName);
             FileOutputStream out = new FileOutputStream(csvFile)) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) out.write(buf, 0, n);
        } catch (IOException notFound) {
            // No seed file in assets → create a new one with header
            try (FileWriter fw = new FileWriter(csvFile, false)) {
                fw.write("id,date,description,calories,fat,carbs,fiber,protein\n");
            } catch (IOException e) {
                android.util.Log.e("LogActivity", "Failed to create entries.csv", e);
            }
        }
    }

    private boolean appendToCsv(LogEntry e) {
        String line = String.format(Locale.US,
                "%d,%s,%s,%d,%d,%d,%d,%d%n",
                e.getId(),
                e.getDate().toString(),
                e.getDescription().replace(",", " "), // simple CSV
                e.getCalories(), e.getFat(), e.getCarbs(), e.getFiber(), e.getProtein());

        try (FileWriter fw = new FileWriter(csvFile, true)) {
            fw.write(line);
            return true;
        } catch (IOException ex) {
            android.util.Log.e("LogActivity", "appendToCsv failed", ex);
            return false;
        }
    }



    private void updateDateHeader() {
        tvDate.setText("Date " + currentDate.format(DATE_FMT));
    }

    private void showDatePicker(final TextView target) {
        // parse existing date or default to today
        String[] parts = target.getText().toString().replace("Date ", "").split("/");
        int m = parts.length>0 ? Integer.parseInt(parts[0]) - 1 : currentDate.getMonthValue()-1;
        int d = parts.length>1 ? Integer.parseInt(parts[1])     : currentDate.getDayOfMonth();
        int y = parts.length>2 ? Integer.parseInt(parts[2])     : currentDate.getYear();

        new DatePickerDialog(this, (view, yy, mm, dd) -> {
            currentDate = LocalDate.of(yy, mm+1, dd);
            updateDateHeader();
            refreshData();
        }, y, m, d).show();
    }

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_weight) {
            startActivity(new Intent(this, WeightActivity.class));
            finish();
            return true;
        } else if (id == R.id.nav_log) {
            return true;  // already here
        } else if (id == R.id.nav_grid) {
            startActivity(new Intent(this, GridActivity.class));
            finish();
            return true;
        }
        return false;
    }

    private void refreshData() {
        // 1) load and display all log entries for currentDate
        List<LogEntry> entries = repo.getEntriesForDate(currentDate);
        adapter.submitList(entries);

        // 2) update totals
        int consumed = repo.getCaloriesConsumed(currentDate);
        int burned   = repo.getCaloriesBurned(currentDate);
        int remaining= repo.getCaloriesRemaining(currentDate);

        tvCaloriesConsumed .setText(String.valueOf(consumed));
        tvCaloriesBurned   .setText(String.valueOf(-burned));
        tvCaloriesRemaining.setText(String.valueOf(remaining));

        // 3) update macros (goal values assumed in repo)
        tvFat   .setText(repo.getFat(currentDate)     + "/" + repo.getFatGoal());
        tvCarbs .setText(repo.getCarbs(currentDate)   + "/" + repo.getCarbsGoal());
        tvFiber .setText(repo.getFiber(currentDate)   + "/" + repo.getFiberGoal());
        tvProtein.setText(repo.getProtein(currentDate)+ "/" + repo.getProteinGoal());
    }

    private void showEntryOptions(LogEntry entry, View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add("Edit entry");
        popup.getMenu().add("Delete entry");
        popup.setOnMenuItemClickListener(item -> {
            String title = item.getTitle().toString();
            if (title.equals("Edit entry")) {
                showEditEntryDialog(entry);
                return true;
            } else if (title.equals("Delete entry")) {
                repo.deleteEntry(entry);
                refreshData();
                return true;
            }
            return false;
        });
        popup.show();
    }


    private void showEditEntryDialog(LogEntry entry) {
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_log_entry, null);

        new AlertDialog.Builder(this)
                .setTitle("Edit entry")
                .setView(view)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (dlg, which) -> {
                    // read new values from dialog, update via repo.updateEntry(...)
                    // repo.updateEntry(entry.getId(), newValues);
                    refreshData();
                })
                .show();
    }
}
