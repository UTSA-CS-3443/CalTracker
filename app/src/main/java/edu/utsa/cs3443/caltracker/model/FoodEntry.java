package edu.utsa.cs3443.caltracker.data;

import android.icu.text.SimpleDateFormat;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class FoodEntry {
    private String name;
    private double calories;
    private double totalFat;

    private double carbs; // total carbohydrates
    private double fiber;
    private double protein;
    private double servingSize;
    private String servingType;
    private Instant logTimestamp;
    // METHODS
    public FoodEntry(String name, double calories, double totalFat, double carbs, double fiber, double protein, double servingSize, String servingType, Instant logTimestamp) {
        this.name = name;
        this.calories = calories;
        this.totalFat = totalFat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.protein = protein;
        this.servingSize = servingSize;
        this.servingType = servingType;
        this.logTimestamp = logTimestamp;
    }

    public FoodEntry(String name, double calories, double carbs, double totalFat, double fiber, double protein, double servingSize, String servingType) {
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
        this.totalFat = totalFat;
        this.fiber = fiber;
        this.protein = protein;
        this.servingSize = servingSize;
        this.servingType = servingType;
        this.logTimestamp = Instant.now();
    }
    // GETTERS
    public String getName() {
        return name;
    }
    public double getCalories() {
        return calories;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFiber() {
        return fiber;
    }

    public double getServingSize() {
        return servingSize;
    }

    public double getProtein() {
        return protein;
    }

    public String getServingType() {
        return servingType;
    }
    public Instant getLogTimestamp() {
        return logTimestamp;
    }
    // SETTERS
    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public void setServingType(String servingType) {
        this.servingType = servingType;
    }
    public void setLogTimestamp(Instant logTimestamp) {
        this.logTimestamp = logTimestamp;
    }
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        String formattedDate = logTimestamp != null ? dateFormat.format(Date.from(logTimestamp)) : "Not logged";

        return String.format(Locale.getDefault(),
                "%s - %.1f cal\n" +
                        "Fat: %.1fg | Carbs: %.1fg | Protein: %.1fg\n" +
                        "Serving: %.1f %s | Logged: %s",
                name, calories, totalFat, carbs, protein,
                servingSize, servingType != null ? servingType : "units",
                formattedDate);
    }

}
