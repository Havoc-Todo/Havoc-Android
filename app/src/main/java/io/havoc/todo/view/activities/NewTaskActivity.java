package io.havoc.todo.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.havoc.todo.R;

public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }
}
