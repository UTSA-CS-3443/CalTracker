# QuizFreaks
<img width="500" height="500" alt="f969bd96-f94d-4196-9000-d36947cb8d7e" src="https://github.com/user-attachments/assets/cfddb0d3-c1e3-4eef-a073-aea214b5ca2c" />

## Description
CalTracker allows users to record their weight, diet, and exercise goals with ease. Input your metrics + goals so that you can stay on track.
## Contributors
- **Ricardo Reyes** - [GitHub](https://github.com/zodingzode)
- **Christopher Delgado** - [Github](https://github.com/PyrotechnicsJY)
- **Samuel Eliot Arnatt** - [Github](https://github.com/SamminatorGaming)

## Installation and Running Instructions

### Prerequisites
- **Software Dependencies**: [Android Studio 2025.1.2.11](https://developer.android.com/studio?gad_source=1&gclid=CjwKCAiA0rW6BhAcEiwAQH28InJ4UeNH9JlpE4vAdedqyPDDtROIxL-SANyk6NMsKIYH1RHhBgR6JBoCa34QAvD_BwE&gclsrc=aw.ds)  
- **Note**: Android Studio will install the latest version of JDK
- **Internet Access**: This application does not require Internet Access

### Setup
Clone the repository inside of Android Studio using the following steps:
1. Copy this HTTPS link taken from inside the project repository:  https://github.com/UTSA-CS-3443/CalTracker.git
2. If an Android Studio project is already opened, go to the top left bars, select file, and select close Project
3. Inside the Welcome to Android Studio window, Select <strong><u>Get from VCS</u></strong>
4. Paste the URL from Step 1 into the URL box. Do not change the pre-selected directory
5. In the bottom right, hit the <strong><u>Clone</u></strong> button
6. Let the application load inside Android studio. There will be a loading bar in the bottom right of Android Studio
7. Once the application has completed loading, press the green play button in the middle of the screen at the top
8. Wait for the application to load, once loaded the application will launch and you are free to use the app

## Guide:
##### User Screen
When you launch the app for the first time, you will be prompted with intro screen so that the app can get to know your metrics and goals. 

##### Weight Screen
Once you fill in the required information you will be greeted with the Weight screen. This is where you can track your body over time by recording your weight for a given day. The screen provides you with a graph for easy visualization of your progress.

##### Log Screen
The Log Screen launcher in found in the middle of the bottom navigation bar. This is where you can track your meals and exercises for that day, in future updates, we will add functionality to modify meals and exercises from previous days. At the top, you are reminded of your daily diet goals, aswell as how close you are to reaching them for the day.

##### Grid Screen
The Grid Screen is where you can save your usual meals and track their calories, that way you can easily log them throughout your day. The app comes default with common foods and their nutrient profiles, such as Eggs, Chicken Breast, Beef, etc... You can add your own favorite foods overtime, creating a little menu for yourself.

## Known Issues:
- Writing to .csv is pending fixes, currently only writes to internal File object to track user data.
- App allows for unrealistic user input (ex: 99999lbs, 0lbs, etc...)

### Potential Future Implementations

- Use names to store photos on a server.
- Edit previous days' meals and exercises.
- Nightmode
