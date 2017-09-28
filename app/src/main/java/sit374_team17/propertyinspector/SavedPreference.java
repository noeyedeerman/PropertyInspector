package sit374_team17.propertyinspector;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Mac on 9/26/2017.
 */

public class SavedPreference {
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context context;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "PropertyInspector";
    private static final String USERNAME= "userName";
    private static final String PASSWORD= "passwords";

    public SavedPreference(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    public void updateCredentials(String usersName,String passwords) {
        editor.putString(USERNAME, usersName);
        editor.putString(PASSWORD, passwords);
        editor.commit();
    }

    public void deleteCredentials() {
        editor.putString(USERNAME, "");
        editor.putString(PASSWORD, "");
        editor.commit();
    }

    public HashMap<String, String> getCredentials() {
        HashMap<String, String> fid = new HashMap<>();
        fid.put("username", pref.getString(USERNAME, ""));
        fid.put("password", pref.getString(PASSWORD, ""));
        return fid;
    }
}
