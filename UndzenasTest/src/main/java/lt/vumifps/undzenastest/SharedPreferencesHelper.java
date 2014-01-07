package lt.vumifps.undzenastest;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private static final String SHARED_PREFERENCES_KEY = "PSPKV-Quiz";

    Context context;

    public SharedPreferencesHelper(Context context) {
        this.context = context;
    }

    public void saveString(String key, String value) {

        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public String loadString(String key) {

        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(key, "");
    }

    private SharedPreferences getSharedPreferences() {
        return this.context.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
        );
    }

    public void clearAll() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
