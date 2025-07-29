package edu.utsa.cs3443.caltracker;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.utsa.cs3443.caltracker.model.FoodEntry;
import edu.utsa.cs3443.caltracker.model.FoodRepository;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int pos = getIntent().getIntExtra("Food_Pos",-1);
        if (pos != -1) {
            FoodRepository repo = new FoodRepository();
            FoodEntry foodEntry = repo.getAllFoods().get(pos);
            TextView des =  findViewById(R.id.des);
            //ImageView food = findViewById(R.id.food);
            Button logButton = findViewById(R.id.button2);

            des.setText(foodEntry.toString());
            logButton.setOnClickListener( v -> {
                // STUB
            });


        }




    }
}