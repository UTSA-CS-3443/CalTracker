Class User {
	private String userName; 
	private String dateOfBirth;
	private int height;
	private int weeklyGoal;
	private Nutrition currNutrition; 
	private ArrayList<Excerise> mealList;
	private ArrayList<Weight> weightChart;
	//Testing to see if works
	
	public User( String userName, String dateOfBirth, int height, int weeklyGoal, ArrayList<Food> mealList, 
	excerciseList ArrayList<Excercise>, Nutrition currNutrition, ArrayList<Weight> weightChart){
	this.userName = userName;
	this.userName = userName;
    this.dateOfBirth = dateOfBirth;
    this.height = height;
    this.weeklyGoal = weeklyGoal;
    this.mealList = mealList;
    this.exerciseList = exerciseList;
    this.currNutrition = currNutrition;
    this.weightChart = weightChart;
	
	}
	
	public User(String userName, String dateOfBirth, int height, int weeklyGoal, 
           Nutrition currNutrition) {
    this.userName = userName;
    this.dateOfBirth = dateOfBirth;
    this.height = height;
    this.weeklyGoal = weeklyGoal;
    this.currNutrition = currNutrition;
    
    
    this.mealList = new ArrayList<>();
    this.exerciseList = new ArrayList<>();
    this.weightChart = new ArrayList<>();
	}
	
	public String getUserName() {
		return userName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public int getHeight() {
		return height;
	}

	public int getWeeklyGoal() {
		return weeklyGoal;
	}

	public ArrayList<Food> getMealList() {
		return mealList;
	}

	public ArrayList<Exercise> getExerciseList() {
		return exerciseList;
	}

	public Nutrition getCurrNutrition() {
		return currNutrition;
	}

	public ArrayList<Weight> getWeightChart() {
		return weightChart;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWeeklyGoal(int weeklyGoal) {
		this.weeklyGoal = weeklyGoal;
	}

	public void setMealList(ArrayList<Food> mealList) {
		this.mealList = mealList;
	}

	public void setExerciseList(ArrayList<Exercise> exerciseList) {
		this.exerciseList = exerciseList;
	}

	public void setCurrNutrition(Nutrition currNutrition) {
		this.currNutrition = currNutrition;
	}

	public void setWeightChart(ArrayList<Weight> weightChart) {
		this.weightChart = weightChart;
	}
	
	public void addMeal(Food food) {
    mealList.add(food);
	}

	public void removeMeal(Food food) {
		mealList.remove(food);
	}

	public void removeMeal(int index) {
		if (index >= 0 && index < mealList.size()) {
			mealList.remove(index);
		}
	}

	public void clearMealList() {
		mealList.clear();
	}

	public int getMealListSize() {
		return mealList.size();
	}

	public Food getMeal(int index) {
		if (index >= 0 && index < mealList.size()) {
			return mealList.get(index);
		}
		return null;
	}


	public void addExercise(Exercise exercise) {
		exerciseList.add(exercise);
	}

	public void removeExercise(Exercise exercise) {
		exerciseList.remove(exercise);
	}

	public void removeExercise(int index) {
		if (index >= 0 && index < exerciseList.size()) {
			exerciseList.remove(index);
		}
	}

	public void clearExerciseList() {
		exerciseList.clear();
	}

	public int getExerciseListSize() {
		return exerciseList.size();
	}

	public Exercise getExercise(int index) {
		if (index >= 0 && index < exerciseList.size()) {
			return exerciseList.get(index);
		}
		return null;
	}


	public void addWeight(Weight weight) {
		weightChart.add(weight);
	}

	public void removeWeight(Weight weight) {
		weightChart.remove(weight);
	}

	public void removeWeight(int index) {
		if (index >= 0 && index < weightChart.size()) {
			weightChart.remove(index);
		}
	}

	public void clearWeightChart() {
		weightChart.clear();
	}

	public int getWeightChartSize() {
		return weightChart.size();
	}

	public Weight getWeight(int index) {
		if (index >= 0 && index < weightChart.size()) {
			return weightChart.get(index);
		}
		return null;
	}

	
	public boolean hasMeals() {
		return !mealList.isEmpty();
	}

	public boolean hasExercises() {
		return !exerciseList.isEmpty();
	}

	public boolean hasWeightData() {
		return !weightChart.isEmpty();
	}
	
}