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
smoketest-yyyy-MM-dd-HH-mm-ss
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

## Jenkins CI

The Jenkins pipeline is in `Jenkinsfile` in this Maven module. It checks out the GitHub repository, runs all four configured TestNG suites with Maven, publishes Surefire JUnit XML, and archives the Surefire and Allure outputs. The build is marked as failed if Maven tests fail, while report generation and artifact publication still run.

### Pipeline architecture and agent requirements

Use a Dockerized Jenkins LTS controller for job orchestration and a separate Linux build agent labelled `android` for the mobile tests. Configure the agent with:

- JDK 17 and Maven 3.9, registered in Jenkins as `JDK17` and `Maven 3.9`
- Node.js, Appium 2.x, and the UiAutomator2 driver
- Android SDK platform tools and a connected Android emulator/device compatible with the sample APK (Android API 28 is recommended)
- An Appium server listening on port 4723, or a reachable remote Appium service

The Jenkins controller container does not need Android SDK, Appium, or an emulator. Keeping device execution on a dedicated agent avoids trying to run an Android emulator inside the controller container. If using a remote Appium service, set the job's `APPIUM_SERVER_URL` parameter to the service URL. Do not place credentials in that URL; configure any required secrets through Jenkins Credentials and the service's supported authentication mechanism.

### Install Jenkins with Docker

On a Docker host, create persistent storage and start the official Jenkins LTS image:

```bash
docker volume create jenkins_home
docker run -d --name jenkins --restart unless-stopped \
  -p 8080:8080 \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts
```

Open `http://localhost:8080`, retrieve the initial administrator password with:

```bash
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

Complete the setup wizard and install the suggested plugins, including Pipeline, Git, GitHub Branch Source, and JUnit. Keep the controller and plugins updated, restrict access to Jenkins, and prefer connecting agents over WebSocket or SSH rather than exposing the inbound agent port.

### Connect the GitHub project and configure the agent

1. Add a Jenkins agent with the label `android`. Provision it on a Linux host that can run the Android emulator or access a physical device and Appium server. Ensure `adb devices` reports the device as `device`, and verify Appium is reachable at the configured URL from the agent.
2. In **Manage Jenkins → Tools**, configure JDK 17 with the name `JDK17` and Maven 3.9 with the name `Maven 3.9`. These names must match the Jenkinsfile.
3. Create a **Multibranch Pipeline** job (recommended for GitHub branch/PR discovery) or a Pipeline job pointing at this repository. Configure the GitHub repository and credentials if it is private. Set the Script Path to `appium-android/Jenkinsfile`.
4. Run the job. For a non-default Appium server, set the `APPIUM_SERVER_URL` build parameter. TestNG and Surefire reports will be available in the Jenkins test results; download the archived `allure-reports` folder and open its `index.html` for the Allure report.

The pipeline archives Allure HTML directly so it works without additional Jenkins reporting plugins. For an inline, navigable Allure trend view, install the Jenkins Allure plugin and configure it to publish `appium-android/target/allure-results` instead.

### Existing Maven commands

Run from `appium-android/` with an emulator/device and Appium server available:

```bash
mvn -B -ntp clean test
```

The Jenkins pipeline uses `mvn clean test` followed by `mvn allure:report` rather than `mvn verify` so an Allure HTML report can still be generated when tests fail. Locally, `mvn clean verify` remains the existing one-command option for running tests and generating Allure output on a successful test run.

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
