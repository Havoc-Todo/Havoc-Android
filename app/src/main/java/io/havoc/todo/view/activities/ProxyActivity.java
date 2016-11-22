package io.havoc.todo.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.havoc.todo.model.PrefKey;
import io.havoc.todo.util.prefs.AuthSharedPrefs;

/**
 * This Activity helps us decide what Activity to advance to depending on auth
 * If the user is already logged in, take them to the MainActivity
 * otherwise, display the LoginActivity
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * If the user is authenticated, take them to the MainActivity
         * Otherwise, take them to the LoginActivity
         */
        if (AuthSharedPrefs.getInstance(this).getBoolean(PrefKey.IS_AUTH, false)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish(); //leave no trace of this ProxyActivity
    }
}
