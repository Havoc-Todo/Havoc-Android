package io.havoc.todo.util;


import android.content.Context;
import android.content.SharedPreferences;

/*
 * A Singleton for managing SharedPreferences.
 *
 * IMPORTANT: The class is not thread safe. It should work fine in most
 * circumstances since the write and read operations are fast. However
 * if you call edit for bulk updates and do not commit your changes
 * there is a possibility of data loss if a background thread has modified
 * preferences at the same time.
 * 
 * Usage:
 * 
 * int sampleInt = SharedPrefsUtil.getInstance(context).getInt(Key.SAMPLE_INT);
 * SharedPrefsUtil.getInstance(context).set(Key.SAMPLE_INT, sampleInt);
 * 
 */
public class SharedPrefsUtil {
    private static final String SETTINGS_NAME = "settings";
    private static SharedPrefsUtil sSharedPrefs;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;

    private SharedPrefsUtil(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Gets an instance of the SharedPrefs
     * If null, create an instance
     *
     * @param context of SharedPrefs
     * @return the SharedPrefs
     */
    public static SharedPrefsUtil getInstance(Context context) {
        if (sSharedPrefs == null) {
            sSharedPrefs = new SharedPrefsUtil(context.getApplicationContext());
        }
        return sSharedPrefs;
    }

    /************ SETTERS ************/

    /**
     * Convenience method for storing Strings
     *
     * @param key The enum of the preference to store
     * @param val The new value for the preference
     */
    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    /**
     * Convenience method for storing ints
     *
     * @param key The enum of the preference to store
     * @param val The new value for the preference
     */
    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    /**
     * Convenience method for storing booleans
     *
     * @param key The enum of the preference to store
     * @param val The new value for the preference
     */
    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }

    /**
     * Convenience method for storing floats
     *
     * @param key The enum of the preference to store
     * @param val The new value for the preference
     */
    public void put(Key key, float val) {
        doEdit();
        mEditor.putFloat(key.name(), val);
        doCommit();
    }

    /**
     * Convenience method for storing doubles
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so casting to and from Strings is
     * needed.
     *
     * @param key The enum of the preference to store
     * @param val The new value for the preference
     */
    public void put(Key key, double val) {
        doEdit();
        mEditor.putString(key.name(), String.valueOf(val));
        doCommit();
    }

    /************ GETTERS ************/

    /**
     * Convenience method for retrieving Strings
     *
     * @param key The enum of the preference to fetch
     */
    public String getString(Key key) {
        return mPref.getString(key.name(), null);
    }

    /**
     * Convenience method for retrieving Strings
     *
     * @param key          The enum of the preference to fetch
     * @param defaultValue to return if the key doesn't have a value
     */
    public String getString(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }

    /**
     * Convenience method for retrieving ints
     *
     * @param key The enum of the preference to fetch
     */
    public int getInt(Key key) {
        return mPref.getInt(key.name(), 0);
    }

    /**
     * Convenience method for retrieving ints
     *
     * @param key          The enum of the preference to fetch
     * @param defaultValue to return if the key doesn't have a value
     */
    public int getInt(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }

    /**
     * Convenience method for retrieving booleans
     *
     * @param key The enum of the preference to fetch
     */
    public boolean getBoolean(Key key) {
        return mPref.getBoolean(key.name(), false);
    }

    /**
     * Convenience method for retrieving booleans
     *
     * @param key          The enum of the preference to fetch
     * @param defaultValue to return if the key doesn't have a value
     */
    public boolean getBoolean(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }

    /**
     * Convenience method for retrieving floats
     *
     * @param key The enum of the preference to fetch
     */
    public float getFloat(Key key) {
        return mPref.getFloat(key.name(), 0);
    }

    /**
     * Convenience method for retrieving floats
     *
     * @param key          The enum of the preference to fetch
     * @param defaultValue to return if the key doesn't have a value
     */
    public float getFloat(Key key, float defaultValue) {
        return mPref.getFloat(key.name(), defaultValue);
    }

    /**
     * Convenience method for retrieving doubles
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so casting to and from Strings is
     * needed.
     *
     * @param key The enum of the preference to fetch
     */
    public double getDouble(Key key) {
        return getDouble(key, 0);
    }

    /**
     * Convenience method for retrieving doubles
     * <p>
     * There may be instances where the accuracy of a double is desired.
     * SharedPreferences does not handle doubles so casting to and from Strings is
     * needed.
     *
     * @param key          The enum of the preference to fetch
     * @param defaultValue to return if the key doesn't have a value
     */
    public double getDouble(Key key, double defaultValue) {
        try {
            return Double.valueOf(mPref.getString(key.name(), String.valueOf(defaultValue)));
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * Remove keys from SharedPreferences
     *
     * @param keys The enum of the key(s) to be removed
     */
    public void remove(Key... keys) {
        doEdit();

        for (Key key : keys) {
            mEditor.remove(key.name());
        }
        doCommit();
    }

    /**
     * Remove all keys from SharedPreferences
     */
    public void clear() {
        doEdit();
        mEditor.clear();
        doCommit();
    }

    /**
     * Create a new SharedPreferences.Editor for this pref
     */
    public void edit() {
        mBulkUpdate = true;
        mEditor = mPref.edit();
    }

    /**
     * Asynchronously commits the changes back to SharedPrefs
     */
    public void commit() {
        mBulkUpdate = false;
        mEditor.apply();
        mEditor = null;
    }

    /**
     * Perform edit() on the SharedPrefs
     * This allows us to atomically commit changes back to SharedPrefs
     */
    private void doEdit() {
        if (!mBulkUpdate && mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    /**
     * Asynchronously commits the changes to SharedPrefs
     */
    private void doCommit() {
        if (!mBulkUpdate && mEditor != null) {
            mEditor.apply();
            mEditor = null;
        }
    }

    /**
     * Enum representing the setting names or key for a setting (pref)
     */
    public enum Key {
        /* Recommended naming convention:
         * ints, floats, doubles, longs:
         * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
         *
         * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
         *
         * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
         */
        IS_AUTH //whether or not the user is authenticated
    }
}
