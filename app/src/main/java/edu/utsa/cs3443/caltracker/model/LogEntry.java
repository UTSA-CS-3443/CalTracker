package edu.utsa.cs3443.caltracker.model;

import java.time.LocalDate;

public class LogEntry {
    private long      id;
    private LocalDate date;
    private String    description;
    private int       calories;   // positive for food, negative for exercise
    private float     fat, carbs, fiber, protein;

    public LogEntry(long id, LocalDate date, String description, int calories,
                    float fat, float carbs, float fiber, float protein) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.protein = protein;
    }

    // --- Getters & setters ---
    public long getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }
    public int    getCalories() { return calories; }
    public float  getFat() { return fat; }
    public float  getCarbs() { return carbs; }
    public float  getFiber() { return fiber; }
    public float  getProtein() { return protein; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setCalories(int calories) { this.calories = calories; }
    public void setFat(float fat) { this.fat = fat; }
    public void setCarbs(float carbs) { this.carbs = carbs; }
    public void setFiber(float fiber) { this.fiber = fiber; }
    public void setProtein(float protein) { this.protein = protein; }
}
