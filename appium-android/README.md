# Android Appium Java project

This project automates the Sauce Labs Mobile Sample app on Android using Appium 2.x, Java 17, Maven, and TestNG.

## Prerequisites

- Java 17
- Maven 3.9+
- Appium 2.x
- Android SDK + emulator, or a physical Android device connected to the machine
- The Sauce Labs sample APK downloaded into `drivers/Android.SauceLabs.Mobile.Sample.app.apk`

## Install dependencies

From the project root:

```bash
mvn clean dependency:go-offline
```

Install Appium and the Android driver:

```bash
npm install -g appium
appium driver install uiautomator2
```

## Start the Android device or emulator

Before running the tests, start an Android emulator or connect a real Android device:

```powershell
adb devices
```

If the emulator is not running, start it from the terminal. On Windows, use the Android SDK emulator folder directly:

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe" -list-avds
& "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe" -avd <device_name>
```

Example:

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe" -list-avds
& "$env:LOCALAPPDATA\Android\Sdk\emulator\emulator.exe" -avd Pixel_7_API_34
```

Verify the emulator is available and visible to ADB:

```powershell
adb devices
```

The output should include a connected device/emulator ID.

Important: this APK targets Android API 28 (Android 9). Use an emulator running API 28 or another compatible Android 9 image; newer emulators can crash the app at startup.

If you do not have an API 28 emulator installed, create one with Android Studio Device Manager or from the command line. Example:

```powershell
& "$env:LOCALAPPDATA\Android\Sdk\cmdline-tools\latest\bin\sdkmanager.bat" "platforms;android-28" "system-images;android-28;google_apis;x86_64"
```

Then create an AVD named, for example, `Pixel_7_API_28`.

## Start the Appium server

Start a local Appium server on port 4723:

```bash
appium --port 4723
```

If you are using a remote Appium service, update the values in `config/appium.properties` and set `automation.server.type=remote` with the remote server URL.

## Run the tests

Start the device/emulator first, then start the Appium server, then run the suite from the project root:

```bash
adb devices
appium --port 4723
mvn clean test
```

To run only one test class:

```bash
mvn test -Dtest=LoginTests
```

## Troubleshooting

- Make sure an Android emulator is running or a physical Android device is connected.
- Confirm the sample APK exists at `drivers/Android.SauceLabs.Mobile.Sample.app.apk`.
- If Appium is not started, the test run will fail with `Connection refused` / `SessionNotCreated` errors.
- This project uses a compatible Appium Java client version (`io.appium:java-client` 8.6.0). Older Appium client versions may cause TestNG discovery issues and zero-test runs.
- If you change the Appium server URL or device details, update `config/appium.properties` before running tests.

## Allure reports

This project is configured to generate an Allure report for every test run. Each run is saved in a timestamped folder using the format:

```text
smoketest-YY-MM-DD-HH-mm-ss
```

Example output folder:

```text
target/allure-reports/smoketest-2026-09-13-20-33-09/index.html
```

Generate the report with:

```bash
mvn clean verify
```

Open the generated `index.html` file in the tagged run folder to view the latest smoke test report.

## Change device capabilities

Edit `config/appium.properties` to change any of the following values:

- `automation.server.type` = `local` or `remote`
- `automation.server.url` = local or remote Appium server URL
- `appium.deviceName` = emulator name or physical device name
- `appium.platformName` = `Android`
- `appium.automationName` = `UiAutomator2`
- `appium.app.path` = path to the APK

Example for a remote Appium service:

```properties
automation.server.type=remote
automation.server.url=https://username:accessKey@ondemand.us-west-1.saucelabs.com:443/wd/hub
appium.deviceName=Google Pixel 7
```

## Project structure

```text
config/
  appium.properties
drivers/
  Android.SauceLabs.Mobile.Sample.app.apk
src/
  main/java/
  test/java/
  test/resources/
```
