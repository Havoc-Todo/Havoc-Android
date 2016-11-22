package io.havoc.todo.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This Activity helps us decide what Activity to advance to depending on auth
 * If the user is already logged in, take them to the MainActivity
 * otherwise, display the LoginActivity
 */
public class ProxyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
