package edu.utsa.cs3443.caltracker.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {
    private final List<LogEntry> entries = new ArrayList<>();

    // Example macro‐goals (you can load these from prefs or DB)
    private final int calorieGoal  = 2000;
    private final int fatGoal      = 50;
    private final int carbsGoal    = 328;
    private final int fiberGoal    = 30;
    private final int proteinGoal  = 164;

    public List<LogEntry> getEntriesForDate(LocalDate date) {
        List<LogEntry> out = new ArrayList<>();
        for (LogEntry e : entries) {
            if (e.getDate().equals(date)) out.add(e);
        }
        return out;
    }

    public int getCaloriesConsumed(LocalDate date) {
        return getEntriesForDate(date).stream()
                .filter(e -> e.getCalories() > 0)
                .mapToInt(LogEntry::getCalories).sum();
    }

    public int getCaloriesBurned(LocalDate date) {
        return getEntriesForDate(date).stream()
                .filter(e -> e.getCalories() < 0)
                .mapToInt(LogEntry::getCalories).sum();
    }

    public int getCaloriesRemaining(LocalDate date) {
        return calorieGoal - getCaloriesConsumed(date) + Math.abs(getCaloriesBurned(date));
    }

    public int getFat(LocalDate date) {
        return (int) getEntriesForDate(date).stream()
                .mapToDouble(LogEntry::getFat).sum();
    }
    public int getFatGoal() { return fatGoal; }

    public int getCarbs(LocalDate date) {
        return (int) getEntriesForDate(date).stream()
                .mapToDouble(LogEntry::getCarbs).sum();
    }
    public int getCarbsGoal() { return carbsGoal; }

    public int getFiber(LocalDate date) {
        return (int) getEntriesForDate(date).stream()
                .mapToDouble(LogEntry::getFiber).sum();
    }
    public int getFiberGoal() { return fiberGoal; }

    public int getProtein(LocalDate date) {
        return (int) getEntriesForDate(date).stream()
                .mapToDouble(LogEntry::getProtein).sum();
    }
    public int getProteinGoal() { return proteinGoal; }

    public void addEntry(LogEntry e) {
        entries.add(e);
    }
    public void updateEntry(LogEntry updated) {
        // find by ID and replace (or update fields)
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getId() == updated.getId()) {
                entries.set(i, updated);
                return;
            }
        }
    }
    public void deleteEntry(LogEntry e) {
        entries.removeIf(x -> x.getId() == e.getId());
    }
}
