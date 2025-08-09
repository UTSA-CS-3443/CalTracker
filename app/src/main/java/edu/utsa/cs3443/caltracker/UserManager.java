package edu.utsa.cs3443.caltracker;

import edu.utsa.cs3443.caltracker.model.FoodRepository;
import edu.utsa.cs3443.caltracker.model.LogRepository;
import edu.utsa.cs3443.caltracker.model.User;
import edu.utsa.cs3443.caltracker.model.WeightRepository;

public class UserManager {
    // Static instance - only one UserManager will exist
    private static UserManager instance;

    // The user object we want to share across activities
    private User currentUser;

    // Private constructor prevents creating multiple instances
    private UserManager() {
        // Empty constructor
    }

    // Method to get the single instance of UserManager
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Set the current user
    public void setUser(User user) {
        this.currentUser = user;
    }

    // Get the current user
    public User getUser() {
        return currentUser;
    }

    // Check if we have a user set
    public boolean hasUser() {
        return currentUser != null;
    }

    // Clear the user (for logout functionality)
    public void clearUser() {
        this.currentUser = null;
    }

    // Get user's name (convenience method)
    public String getUserName() {
        return currentUser != null ? currentUser.getName() : "No User";
    }

    // Get user's repositories (convenience methods)
    public FoodRepository getFoodRepository() {
        return currentUser != null ? currentUser.getFoodRepo() : null;
    }

    public LogRepository getLogRepository() {
        return currentUser != null ? currentUser.getLogRepo() : null;
    }

    public WeightRepository getWeightRepository() {
        return currentUser != null ? currentUser.getWeightRepo() : null;
    }
}
