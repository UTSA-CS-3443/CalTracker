package edu.utsa.cs3443.caltracker;

import android.health.connect.datatypes.ExercisePerformanceGoal;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import edu.utsa.cs3443.caltracker.model.*;
public class UserActivity extends AppCompatActivity {

    private TextInputEditText nameEdit;
    private TextInputEditText heightEdit;
   private TextInputEditText dateOfBirth;
   private TextInputEditText goalWeightEdit;
    private TextInputEditText fiberGoalEdit;
    private TextInputEditText proteinGoalEdit;
    private TextInputEditText carbGoalEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        initializeViews();

       Button submitButton = findViewById(R.id.submitButton);
       submitButton.setOnClickListener( v -> {
        createUser(v);


    });
    }

    private void initializeViews() {
        nameEdit = findViewById(R.id.nameEdit);
        heightEdit = findViewById(R.id.heightEdit);
        goalWeightEdit = findViewById(R.id.weightGoal);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        fiberGoalEdit = findViewById(R.id.fiberGoal);
        proteinGoalEdit = findViewById(R.id.proteinGoal);
        carbGoalEdit = findViewById(R.id.carbGoal);
    }

    // Method to create user from input data
    public void createUser(View view) {
        if (validateInputs()) {
            try {
                // Get values from EditTexts (only name, height, weight)
                String name = Objects.requireNonNull(nameEdit.getText()).toString().trim();
                int height = Integer.parseInt(Objects.requireNonNull(heightEdit.getText()).toString().trim());
                double weeklyGoal = Double.parseDouble(Objects.requireNonNull(goalWeightEdit.getText()).toString().trim());
                String birthday = Objects.requireNonNull(dateOfBirth.getText()).toString().trim();
                // Create User object with basic info only
                User newUser = new User(name,birthday,height,weeklyGoal);

                // Store user using singleton
                UserManager.getInstance().setUser(newUser);

                // Navigate to main app activity
                Intent intent = new Intent(UserActivity.this, GridActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(this, "User created successfully!", Toast.LENGTH_SHORT).show();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(this,"Please complete all forms", Toast.LENGTH_SHORT).show();
            }
        }
        }

    private boolean validateInputs() {
        // Check if name is entered
        if (TextUtils.isEmpty(nameEdit.getText())) {
            nameEdit.setError("Name is required");
            nameEdit.requestFocus();
            return false;
        }

        // Check if height is entered
        if (TextUtils.isEmpty(heightEdit.getText())) {
            heightEdit.setError("Height is required");
            heightEdit.requestFocus();
            return false;
        }

        // Check if date of birth is entered
        if (TextUtils.isEmpty(dateOfBirth.getText())) {
            dateOfBirth.setError("Date of birth is required");
            dateOfBirth.requestFocus();
            return false;
        }

        // Check if goal weight is entered
        if (TextUtils.isEmpty(goalWeightEdit.getText())) {
            goalWeightEdit.setError("Goal weight is required");
            goalWeightEdit.requestFocus();
            return false;
        }

        // Validate date format (basic check for YYYY/MM/DD)
        String dateText = dateOfBirth.getText().toString().trim();
        if (!dateText.matches("\\d{4}/\\d{2}/\\d{2}")) {
            dateOfBirth.setError("Please use format YYYY/MM/DD");
            dateOfBirth.requestFocus();
            return false;
        }

        return true;
    }

    // Method to clear all fields

}
