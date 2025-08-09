package edu.utsa.cs3443.caltracker.model;

import java.time.LocalDate;

public class LogEntry {
    private long      id;
    private LocalDate date;
    private String    description;
    private int       calories;   // positive for food, negative for exercise
    private int     fat, carbs, fiber, protein;

    public LogEntry(long id, LocalDate date, String description, int calories,
                    int fat, int carbs, int fiber, int protein) {
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
    public int  getFat() { return fat; }
    public int  getCarbs() { return carbs; }
    public int  getFiber() { return fiber; }
    public int  getProtein() { return protein; }

    public void setDate(LocalDate date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
    public void setCalories(int calories) { this.calories = calories; }
    public void setFat(int fat) { this.fat = fat; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public void setFiber(int fiber) { this.fiber = fiber; }
    public void setProtein(int protein) { this.protein = protein; }
}
