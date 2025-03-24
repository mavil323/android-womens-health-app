Women's health Tracker App

A modern, user-friendly Android period tracker app designed to help users track their menstrual cycle, predict fertile days, and manage their health. The app includes a custom-designed calendar with a soothing lavender theme.
🚀 Features

✅ Custom CalendarView with a lavender theme
✅ Tracks period dates, fertile window, and next predicted period
✅ Sign-Up / Log-In system using Firebase Authentication
✅ Pop-up dialog prompting Sign-Up / Log-In on the first launch
✅ Uses SharedPreferences to track first-time users
✅ Designed with intuitive UI for smooth user experience
🛠️ Technologies Used

    Kotlin (Primary language)

    Firebase Authentication (User sign-up/login)

    CalendarView (Custom calendar with period/fertility highlights)

    SharedPreferences (To track first-time users)

    Material Design Components (UI styling)

    Android Studio (IDE)

🔹 Installation Steps

    Clone the Repository

git clone https://github.com/YourUsername/Period-Tracker-App.git
cd Period-Tracker-App

Open the Project in Android Studio

    In Android Studio, select "Open an Existing Project".

    Navigate to the folder where you cloned the repository.

Add Firebase Configuration

    Go to the Firebase Console.

    Register your app with your package name.

    Download the google-services.json file.

    Place the google-services.json file in the /app/ directory.

Update Dependencies

    In your build.gradle.kts (Project), ensure this is added:

classpath("com.google.gms:google-services:4.3.15")

In your build.gradle.kts (App), ensure this is added:

        plugins {
            id("com.google.gms.google-services")
        }

    Sync Project with Gradle Files

        Go to File → Sync Project with Gradle Files.

    Run the App

        Select your preferred emulator or device and click Run ▶️.

🔹 How to Use

    First Launch:

        Upon opening the app for the first time, a Sign-Up/Log-In pop-up will appear.

        Users can either:

        ➡️ Sign Up to create a new account.

        ➡️ Log In to access their account.

        ➡️ Skip to explore the app without registration.

    Period Tracking:

        Select your period start date in the calendar.

        The app will predict:

        🔹 Next Period Date

        🔹 Fertile Window

    Custom Calendar Design:

        🌸 Lavender background for a calming look.

        🔴 Period Dates marked in pink.

        🟢 Fertile Window marked in light green.
