# SensorLabApp

An Android app template for reading sensors and location data.

## Lab Task
By default, the app uses the **light sensor**.  Pressing the button provided will output one reading from the light sensor
Your task is to edit the code in MainActivity.kt such that the app takes a reading from the **accelerometer** sensor instead.

The segments of code you need to modify are marked out by comments:
 // Modify this
 // details~

 ## Step-by-Step Installation Guide for Students**

### **Prerequisites**
- Android Studio (latest stable version)
- An Android phone (USB debugging enabled)

---

### **Steps**

1. **Open Android Studio**
   - Click **“Get from VCS”** on the welcome screen.
   - Paste this repository URL:
     ```
     https://github.com/urynb/SensorLabApp.git
     ```
   - Click **Clone**.

2. **Wait for Gradle sync**
   - Let Android Studio finish downloading dependencies (bottom bar progress).

3. **Connect your Android device**
   - On your phone, go to:
     ```
     Settings → Developer options → USB debugging → ON
     ```
   - Connect via USB, and allow debugging permissions.
  
    - If this does not work or you don't have a USB cable, build an APK and download that on your phone.

4. **Build the project**
   - Click **Build → Make Project**   
   - Wait for “Build successful”.

5. **Run the app**
   - Choose your phone or emulator in the **device selector**.
   - Click **Run ▶️**.

6. **Grant permissions**
   - When prompted, allow **Location** permission.
   - Tap the **Button** to display sensor data.

