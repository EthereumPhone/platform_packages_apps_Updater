package app.seamlessupdate.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

import java.util.UUID;

public class DIUtils {

    private static final String PREFS_NAME = "DeviceIdentifierPrefs";
    private static final String DEVICE_IDENTIFIER_KEY = "deviceIdentifier";

    public static String getDeviceIdentifier(Context context) {
        // Try to retrieve the device identifier from SharedPreferences
        String savedIdentifier = getSavedIdentifier(context);

        if (savedIdentifier != null && !savedIdentifier.isEmpty()) {
            return savedIdentifier;
        } else {
            // If no saved identifier exists, try to get the Android ID
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            // Make sure the Android ID is not null or empty
            if (androidId != null && !androidId.isEmpty()) {
                // Save the Android ID for future use
                saveIdentifier(context, androidId);
                return androidId;
            } else {
                // If Android ID is not available, generate and save a random identifier
                String randomIdentifier = generateRandomIdentifier();
                saveIdentifier(context, randomIdentifier);
                return randomIdentifier;
            }
        }
    }

    private static String getSavedIdentifier(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(DEVICE_IDENTIFIER_KEY, null);
    }

    private static void saveIdentifier(Context context, String identifier) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DEVICE_IDENTIFIER_KEY, identifier);
        editor.apply();
    }

    private static String generateRandomIdentifier() {
        // Generate a random identifier (for cases where Android ID is not available)
        // You can use any method you prefer to generate a random string
        // Here's a simple example using java.util.UUID:
        return UUID.randomUUID().toString();
    }
}
