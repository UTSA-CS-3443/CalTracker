package edu.utsa.cs3443.caltracker.model;

public class User {
    private String dateOfBirth;
    private int height;

    private double weeklyGoal;

    //private Nutrition currNutrition
    // Log

    private  FoodRepository foodRepo;
    private LogRepository logRepo;
    private WeightRepository weightRepo;

    public User(double weeklyGoal, String dateOfBirth, int height) {
        this.weeklyGoal = weeklyGoal;
        this.dateOfBirth = dateOfBirth;
        this.height = height;

        this.foodRepo =  new FoodRepository();
        this.logRepo = new LogRepository();
        this.weightRepo = new WeightRepository();

    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(double weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    // all the repos have their own methods to access the arraylists, and loading them from the internal storage in their own class
}
