package edu.utsa.cs3443.caltracker;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.Instant;

import edu.utsa.cs3443.caltracker.model.FoodEntry;
import edu.utsa.cs3443.caltracker.model.FoodRepository;
import edu.utsa.cs3443.caltracker.ui.FoodAdapter;
import edu.utsa.cs3443.caltracker.ui.OnFoodClickListener;

public class GridActivity extends AppCompatActivity  implements OnFoodClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set basic layout
        setContentView(R.layout.activity_grid);
        TextView grid_tile = findViewById(R.id.grid_title);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        // for testing
        RecyclerView recyclerView = findViewById(R.id.dataBaseGrid);


        // setup food repo and adapter
        FoodRepository fRepo = new FoodRepository();
        FoodAdapter fAdapter = new FoodAdapter(this, fRepo, this);
        recyclerView.setAdapter(fAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Log.d("GridActivity", "Food count: " + fRepo.getAllFoods().size());
        // Navigation bar at the bottom
        bottomNav.setOnItemSelectedListener(this::onNavItemSelected);

        //buttons
        Button addFoodToDb = findViewById(R.id.addFoodToDB);
        addFoodToDb.setOnClickListener( v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialogView = inflater.inflate(R.layout.addfoodtodb, null);

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add Food Entry")
                    .setView(dialogView)
                    .setPositiveButton("Add", (d, which) -> {
                        validateAndCreateFoodEntry(dialogView); // Pass the dialogView here
                    })
                    .setNegativeButton("Cancel", null)
                    .create();

            dialog.show();
        });

    }



    @Override
    public void onFoodClick(int position) {
        Log.d( "GridActivity", "onFoodClick " + position);
        Toast.makeText(this, "Clicked item at position " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Food_Pos",position);
        startActivity(intent);
    }
    private boolean onNavItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_weight) {
            startActivity(new Intent(this, WeightActivity.class));
            return true;
        } else if (id == R.id.nav_log) {
            startActivity(new Intent(this, LogActivity.class));
            return true;
        } else return id == R.id.nav_grid;
    }


    private boolean validateAndCreateFoodEntry(View dialogView) {
        try {
            // Get all input values
            EditText nameEdit = dialogView.findViewById(R.id.edit_food_name);
            EditText caloriesEdit = dialogView.findViewById(R.id.edit_calories);
            EditText fatEdit = dialogView.findViewById(R.id.edit_total_fat);
            EditText carbsEdit = dialogView.findViewById(R.id.edit_carbs);
            EditText fiberEdit = dialogView.findViewById(R.id.edit_fiber);
            EditText proteinEdit = dialogView.findViewById(R.id.edit_protein);
            EditText servingSizeEdit = dialogView.findViewById(R.id.edit_serving_size);
            EditText servingTypeEdit = dialogView.findViewById(R.id.edit_serving_type);

            String name = nameEdit.getText().toString().trim();
            String caloriesStr = caloriesEdit.getText().toString().trim();
            String fatStr = fatEdit.getText().toString().trim();
            String carbsStr = carbsEdit.getText().toString().trim();
            String fiberStr = fiberEdit.getText().toString().trim();
            String proteinStr = proteinEdit.getText().toString().trim();
            String servingSizeStr = servingSizeEdit.getText().toString().trim();
            String servingType = servingTypeEdit.getText().toString().trim();

            // Validate required fields
            if (name.isEmpty()) {
                nameEdit.setError("Food name is required");
                nameEdit.requestFocus();
                return false;
            }

            if (caloriesStr.isEmpty()) {
                caloriesEdit.setError("Calories are required");
                caloriesEdit.requestFocus();
                return false;
            }


            if (servingSizeStr.isEmpty()) {
                servingSizeEdit.setError("Serving size is required");
                servingSizeEdit.requestFocus();
                return false;
            }

            if (servingType.isEmpty()) {
                servingTypeEdit.setError("Serving type is required");
                servingTypeEdit.requestFocus();
                return false;
            }

            // Parse numeric values with defaults for optional fields
            double calories = Double.parseDouble(caloriesStr);
            double totalFat = fatStr.isEmpty() ? 0.0 : Double.parseDouble(fatStr);
            double carbs = carbsStr.isEmpty() ? 0.0 : Double.parseDouble(carbsStr);
            double fiber = fiberStr.isEmpty() ? 0.0 : Double.parseDouble(fiberStr);
            double protein = proteinStr.isEmpty() ? 0.0 : Double.parseDouble(proteinStr);
            double servingSize = Double.parseDouble(servingSizeStr);

            // Create FoodEntry object
            FoodEntry foodEntry = new FoodEntry(
                    name,
                    calories,
                    totalFat,
                    carbs,
                    fiber,
                    protein,
                    servingSize,
                    servingType,
                    Instant.now() // Current timestamp
            );




            Toast.makeText(this, "Food entry added successfully!", Toast.LENGTH_SHORT).show();
            return true;

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return false;
        } catch (Exception e) {
            Toast.makeText(this, "Error creating food entry: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}


