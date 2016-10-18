package io.havoc.todo.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.presenter.MainActivityPresenter;
import io.havoc.todo.view.MainActivityView;

public class MainActivity extends TiActivity<MainActivityPresenter, MainActivityView> implements MainActivityView {

    //    private static final String FRAGMENT_LIST_VIEW = "List view";
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
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data.getBooleanExtra("addedNewTask", false)) {
                //Refresh the list upon returning
                refreshList = true;
            }
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fabNewTask.setOnClickListener(view -> getPresenter().newTaskButtonClicked());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new ListFragment(), FRAGMENT_LIST_VIEW)
//                    .commit();
//        }
    }
}
