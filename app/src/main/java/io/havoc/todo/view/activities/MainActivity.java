package io.havoc.todo.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.model.PrefKey;
import io.havoc.todo.presenter.MainActivityPresenter;
import io.havoc.todo.util.prefs.AuthSharedPrefs;
import io.havoc.todo.util.prefs.SettingsSharedPrefs;
import io.havoc.todo.view.MainActivityView;
import io.havoc.todo.view.fragments.ListFragment;

public class MainActivity extends TiActivity<MainActivityPresenter, MainActivityView> implements MainActivityView {

    private static final String FRAGMENT_TASK_LIST = "Task List";
    public boolean refreshList = false; //whether or not to refresh the List
    @BindView(R.id.fab_add)
    public FloatingActionButton fabNewTask;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @NonNull
    @Override
    public MainActivityPresenter providePresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void launchNewTaskActivity() {
        Intent newTaskIntent = new Intent(this, NewTaskActivity.class);
        this.startActivityForResult(newTaskIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("addedNewTask", false)) {
                //Refresh the list upon returning
                refreshList = true;
            }
        }
    }

    @Override
    public void logout() {
        AuthSharedPrefs.getInstance(this).clear(); //delete all AuthPrefs
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void sortByPriority() {
        boolean sortedByPriority = SettingsSharedPrefs.getInstance(this).getBoolean(PrefKey.IS_SORTED_PRIORITY, false);
        //flip it each time the menu item is selected
        SettingsSharedPrefs.getInstance(this).put(PrefKey.IS_SORTED_PRIORITY, !sortedByPriority);

        ((ListFragment) (getSupportFragmentManager())
                .findFragmentByTag(FRAGMENT_TASK_LIST)).getPresenter().loadTaskList(!sortedByPriority);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fabNewTask.setOnClickListener(view -> getPresenter().newTaskButtonClicked());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.task_fragment_container, new ListFragment(), FRAGMENT_TASK_LIST)
                    .commit();
        }

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_priority:
                sortByPriority();
                return true;
            case R.id.action_logout:
                logout();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
