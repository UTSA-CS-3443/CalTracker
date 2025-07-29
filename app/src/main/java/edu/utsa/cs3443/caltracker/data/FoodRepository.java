package edu.utsa.cs3443.caltracker.data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FoodRepository {
    private final ArrayList<FoodEntry> foodEntries = new ArrayList<>();

    public FoodRepository( ){
        // for testing layout
        foodEntries.add( new FoodEntry("Chicken breast",165,3.5,0,0,30.9,100,"grams", Instant.now() ) );
        foodEntries.add(new FoodEntry("Beef (80% lean)", 253, 20.3, 0, 0, 16.8, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Rice (white)", 130, 0.3, 28.1, 0.3, 2.7, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Broccoli", 34, 0.3, 4.5, 2.5, 2.8, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Mixed Berries", 50, 0.4, 8.5, 3.6, 0.7, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Mixed Nuts", 607, 52.9, 14.1, 7.1, 21.2, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Greek Yogurt", 101, 4.9, 4, 0, 9.7, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Eggs", 144, 9.6, 0.8, 0, 12, 2, "Large Eggs", Instant.now()));
        foodEntries.add(new FoodEntry("Beans", 126, 0.5, 16.4, 6.2, 8.5, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Milk", 65, 3.5, 5.3, 0, 3.5, 8, "fl oz", Instant.now()));
        foodEntries.add(new FoodEntry("Chocolate", 484, 29, 58.1, 0, 6.5, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Bread (white)", 264, 3.2, 45.2, 2.8, 9.2, 100, "grams", Instant.now()));
        foodEntries.add(new FoodEntry("Salmon", 131, 4.5, 0, 0, 22.9, 100, "grams", Instant.now()));
    }

    public List<FoodEntry> getAllFoods(){
        return  new ArrayList<>(foodEntries);
    }
    public void addFood( FoodEntry f ){foodEntries.add(f);}
    public void deleteFood (FoodEntry f){foodEntries.remove(f);}
    public void updateFood (FoodEntry f , Instant timestamp ){f.setLogTimestamp( timestamp );}

}
