package edu.utsa.cs3443.caltracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.components.XAxis;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.utsa.cs3443.caltracker.model.WeightEntry;
import edu.utsa.cs3443.caltracker.model.WeightRepository;
import edu.utsa.cs3443.caltracker.ui.WeightAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class WeightActivity extends AppCompatActivity {
    private WeightRepository repo;
    private WeightAdapter adapter;
    private LineChart chart;
    private TextView tvStart, tvEnd;
    private RecyclerView rvEntries;
    private Button btnAddNew;
    private BottomNavigationView bottomNav;
    private LocalDate filterStart = null;
    private LocalDate filterEnd   = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        // find views
        chart = findViewById(R.id.lineChart);
        tvStart = findViewById(R.id.tvStartDate);
        tvEnd = findViewById(R.id.tvEndDate);
        btnAddNew = findViewById(R.id.btnAddNew);
        rvEntries = findViewById(R.id.rvEntries);
        bottomNav = findViewById(R.id.bottomNav);

        // init repo & adapter
        repo = new WeightRepository();
        adapter = new WeightAdapter((entry, anchor) -> showEntryOptions(entry, anchor)); //Todo: figure this out, see if it can be removed
        rvEntries.setLayoutManager(new LinearLayoutManager(this));
        rvEntries.setAdapter(adapter);

        // wire clicks
        tvStart.setOnClickListener(v -> showDatePicker(tvStart));
        tvEnd.setOnClickListener(v -> showDatePicker(tvEnd));
        btnAddNew.setOnClickListener(v -> showAddWeightDialog());
        bottomNav.setOnNavigationItemSelectedListener(this::onNavItemSelected);

        // initial load
        refreshData();
    }

    private boolean onNavItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_weight) {
            return true;
        } else if (id == R.id.nav_log) {
            startActivity(new Intent(this, LogActivity.class));
            return true;
        } else if (id == R.id.nav_grid) {
            startActivity(new Intent(this, GridActivity.class));
            return true;
        }
        return false;
    }


    private void refreshData() {
        //  Get & sort everything
        List<WeightEntry> all = repo.getAllWeights();
        all.sort(Comparator.comparing(WeightEntry::getDate));
        if (all.isEmpty()) return;

        //  Make a filtered copy JUST for the chart
        List<WeightEntry> chartData = new ArrayList<>();
        for (WeightEntry w : all) {
            boolean okStart = (filterStart == null) || !w.getDate().isBefore(filterStart);
            boolean okEnd   = (filterEnd   == null) || !w.getDate().isAfter(filterEnd);
            if (okStart && okEnd) chartData.add(w);
        }
        if (chartData.isEmpty()) {
            chart.clear();
            chart.invalidate();
        } else {
            //  Update start/end labels based on chart range
            tvStart.setText(chartData.get(0).formattedDate());
            tvEnd.setText(chartData.get(chartData.size() - 1).formattedDate());

            //  Build entries using real date
            List<Entry> entries = new ArrayList<>();
            for (WeightEntry w : chartData) {
                float x = (float) w.getDate().toEpochDay();
                entries.add(new Entry(x, (float) w.getWeight()));
            }

            LineDataSet set = new LineDataSet(entries, "Weight");
            int black = androidx.core.content.ContextCompat.getColor(this, R.color.black);
            set.setColor(black);
            set.setDrawCircles(false);
            set.setLineWidth(2f);

            chart.setData(new LineData(set));

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
                @Override public String getFormattedValue(float value) {
                    return LocalDate.ofEpochDay((long) value)
                            .format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                }
            });

            chart.getAxisRight().setEnabled(false);
            chart.getDescription().setEnabled(false);
            chart.getLegend().setEnabled(false);
            chart.invalidate();
        }

        //  shows all entries (newest first)
        List<WeightEntry> listData = new ArrayList<>(all);
        Collections.reverse(listData);
        adapter.forceSubmit(listData);
        rvEntries.scrollToPosition(0);

    }



    private void showDatePicker(final TextView target) {
        // parse existing or default to today
        LocalDate now = LocalDate.now();
        String[] parts = target.getText().toString().split("/");
        int m = parts.length > 0 ? Integer.parseInt(parts[0]) - 1 : now.getMonthValue() - 1;
        int d = parts.length > 1 ? Integer.parseInt(parts[1]) : now.getDayOfMonth();
        int y = parts.length > 2 ? Integer.parseInt(parts[2]) : now.getYear();

        new DatePickerDialog(this, (view, yy, mm, dd) -> {
            LocalDate nd = LocalDate.of(yy, mm + 1, dd);
            target.setText(nd.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            if (target.getId() == R.id.tvStartDate) filterStart = nd;
            else filterEnd = nd;
            refreshData();
        }, y, m, d).show();

    }

    private void showAddWeightDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_weight, null);
        EditText et = view.findViewById(R.id.etWeight);
        TextView title = view.findViewById(R.id.tvDialogTitle);
        title.setText(R.string.button_add_new_weight);
        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        new AlertDialog.Builder(this)
                .setView(view)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (d, wBtn) -> {
                    double w = Double.parseDouble(et.getText().toString());
                    repo.addWeight(new WeightEntry(LocalDate.now(), w));
                    refreshData();
                })
                .show();
    }


    private void showEntryOptions(WeightEntry entry, View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenu().add("Edit Date");
        popup.getMenu().add("Edit Weight");
        popup.getMenu().add("Delete");
        popup.setOnMenuItemClickListener(item -> {
            String t = item.getTitle().toString();
            if (t.equals("Edit Date")) showEditDateDialog(entry);
            else if (t.equals("Edit Weight")) showEditWeightDialog(entry);
            else {
                repo.deleteWeight(entry);
                refreshData();
            }
            return true;
        });
        popup.show();
    }

    private void showEditDateDialog(WeightEntry entry) {
        LocalDate current = entry.getDate();
        DatePickerDialog dlg = new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    LocalDate newDate = LocalDate.of(y, m + 1, d);
                    repo.updateDate(entry, newDate);   // stub repo for now
                    refreshData();
                    Toast.makeText(this, "Date updated", Toast.LENGTH_SHORT).show();
                },
                current.getYear(),
                current.getMonthValue() - 1,
                current.getDayOfMonth()
        );
        dlg.show();
    }

    private void showEditWeightDialog(WeightEntry entry) {
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_weight, null);
        EditText et = view.findViewById(R.id.etWeight);
        TextView title = view.findViewById(R.id.tvDialogTitle);
        title.setText(R.string.edit_weight);
        et.setText(String.valueOf(entry.getWeight()));
        et.setSelection(et.getText().length());
        et.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        new AlertDialog.Builder(this)
                .setView(view)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, (d, wBtn) -> {
                    double v = Double.parseDouble(et.getText().toString());
                    repo.updateWeight(entry, v);
                    refreshData();
                })
                .show();
    }
}

