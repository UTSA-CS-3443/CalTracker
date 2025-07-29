package edu.utsa.cs3443.caltracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import edu.utsa.cs3443.caltracker.model.LogAdapter;
import edu.utsa.cs3443.caltracker.model.LogEntry;
import edu.utsa.cs3443.caltracker.model.LogRepository;

public class LogActivity extends AppCompatActivity {

    private TextView tvDate;
    private TextView tvCaloriesConsumed, tvCaloriesBurned, tvCaloriesRemaining;
    private TextView tvFat, tvCarbs, tvFiber, tvProtein;
    private RecyclerView rvLogEntries;
    private BottomNavigationView bottomNav;

    private LogRepository repo;
    private LogAdapter adapter;
    private LocalDate currentDate;

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("M/d/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // --- find views ---
        tvDate               = findViewById(R.id.tvDate);
        tvCaloriesConsumed   = findViewById(R.id.tvCaloriesConsumed);
        tvCaloriesBurned     = findViewById(R.id.tvCaloriesBurned);
        tvCaloriesRemaining  = findViewById(R.id.tvCaloriesRemaining);
        tvFat                = findViewById(R.id.tvFat);
        tvCarbs              = findViewById(R.id.tvCarbs);
        tvFiber              = findViewById(R.id.tvFiber);
        tvProtein            = findViewById(R.id.tvProtein);
        rvLogEntries         = findViewById(R.id.rvLogEntries);
        bottomNav            = findViewById(R.id.bottomNav);

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

        // hardcode for demo purposes
        repo.addEntry(new LogEntry(
                1,
                LocalDate.of(2025, 6, 24),
                "Meal 1: 230 Cal",
                230,
                1.5f,7f,3f,75f
        ));
        repo.addEntry(new LogEntry(
                2,
                LocalDate.of(2025, 6, 24),
                "30 g Cereal Ex 150 Cal",
                150,
                1.5f,7f,3f,30f
        ));
        repo.addEntry(new LogEntry(
                3,
                LocalDate.of(2025, 6, 24),
                "45 g Quick Add 180 Cal",
                180,
                0f,0f,0f,45f
        ));
        repo.addEntry(new LogEntry(
                4,
                LocalDate.of(2025, 6, 24),
                "Exercise 1 -100 Cal",
                -100,
                0f,0f,0f,0f
        ));
        repo.addEntry(new LogEntry(
                5,
                LocalDate.of(2025, 6, 24),
                "Meal 2: 330 Cal",
                330,
                1.5f,7f,3f,75f
        ));

        // --- load data for today ---
        currentDate = LocalDate.of(2025, 6, 24);
        updateDateHeader();
        refreshData();


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
        // e.g. EditText etAmount = view.findViewById(R.id.etAmount);
        //      etAmount.setText(String.valueOf(entry.getAmount()));
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
