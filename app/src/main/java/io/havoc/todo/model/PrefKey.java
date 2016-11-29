package io.havoc.todo.model;

/**
 * Enum representing the setting names or key for a setting (pref)
 */
public enum PrefKey {
    /* Recommended naming convention:
     * ints, floats, doubles, longs:
     * SAMPLE_NUM or SAMPLE_COUNT or SAMPLE_INT, SAMPLE_LONG etc.
     *
     * boolean: IS_SAMPLE, HAS_SAMPLE, CONTAINS_SAMPLE
     *
     * String: SAMPLE_KEY, SAMPLE_STR or just SAMPLE
     */

    IS_AUTH, //whether or not the user is authenticated
    GOOGLE_USER_EMAIL, //user's Google email address
    USER_ID, //userID for tasks is their email like so: user-host

    IS_SORTED_PRIORITY //whether or not the list is sorted by priority
}
