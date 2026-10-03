package com.saucelabs.appium.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class AppiumConfig {
    private static final String DEFAULT_CONFIG_PATH = "config/appium.properties";
    private static final Properties PROPERTIES = loadProperties();

    private AppiumConfig() {
        // Utility class
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        String configPath = System.getProperty("appium.config", DEFAULT_CONFIG_PATH);
        Path path = Paths.get(configPath).toAbsolutePath().normalize();

        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read Appium configuration from: " + path, e);
        }

        return properties;
    }

    public static String getServerType() {
        return get("automation.server.type", "local");
    }

    public static String getServerUrl() {
        return get("automation.server.url", "http://127.0.0.1:4723");
    }

    public static String getPlatformName() {
        return get("appium.platformName", "Android");
    }

    public static String getAutomationName() {
        return get("appium.automationName", "UiAutomator2");
    }

    public static String getDeviceName() {
        return get("appium.deviceName", "Android Emulator");
    }

    public static String getAppPath() {
        String configuredPath = get("appium.app.path", "drivers/Android.SauceLabs.Mobile.Sample.app.apk");
        Path path = Paths.get(configuredPath);

        if (!path.isAbsolute()) {
            path = Paths.get(System.getProperty("user.dir"), configuredPath);
        }

        return path.normalize().toString();
    }

    public static String getAppPackage() {
        return get("appium.appPackage", "com.swaglabsmobileapp");
    }

    public static String getAppActivity() {
        return get("appium.appActivity", "com.swaglabsmobileapp.SplashActivity");
    }

    public static int getImplicitWaitSeconds() {
        return Integer.parseInt(get("appium.implicitWaitSeconds", "10"));
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, PROPERTIES.getProperty(key, defaultValue));
    }
}
