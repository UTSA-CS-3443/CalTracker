package edu.utsa.cs3443.caltracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.utsa.cs3443.caltracker.data.FoodRepository;
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

        bottomNav.setOnItemSelectedListener(this::onNavItemSelected);

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
}


