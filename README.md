# Team6 Metal Detector Android App

A modern Android application that simulates a metal detector using device sensors and provides an interactive user experience.

## Features

- **Metal Detection Simulation**: Uses device sensors to simulate metal detection
- **Compass Integration**: Real-time compass functionality for directional detection
- **Interactive UI**: Modern Material Design interface with smooth animations
- **Sound Effects**: Audio feedback for detection events
- **Radar Animation**: Visual radar display with GIF animations

## Technical Details

- **Target SDK**: Android API 33
- **Minimum SDK**: Android API 21 (Android 5.0)
- **Language**: Java
- **Architecture**: Standard Android Activity-based architecture

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/team6metaldetector/
│   │   ├── MainActivity.java          # Main entry point
│   │   └── MetalDetectorActivity.java # Core metal detection logic
│   ├── res/
│   │   ├── layout/                    # UI layouts
│   │   ├── drawable/                  # Images and graphics
│   │   ├── raw/                       # Audio files
│   │   └── values/                    # Strings, colors, themes
│   └── AndroidManifest.xml           # App configuration
```

## Requirements

- Android Studio Arctic Fox or later
- Android SDK API 33
- Java 17 or later
- Android device or emulator with compass sensor

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/sahiljain1234/Team6MetalDetector.git
   cd Team6MetalDetector
   ```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

## Building the App

```bash
# Debug build
./gradlew assembleDebug

# Install on connected device/emulator
./gradlew installDebug
```

## Permissions

The app requires the following permissions:
- `INTERNET` - For potential future network features
- `android.hardware.sensor.compass` - For compass functionality

## Dependencies

- AndroidX AppCompat
- Material Design Components
- Android GIF Drawable Library
- JUnit for testing
- Espresso for UI testing

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Team

Developed by Team 6 for the AI Startup project.
