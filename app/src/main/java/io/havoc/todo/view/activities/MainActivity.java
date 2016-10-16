package io.havoc.todo.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.presenter.MainActivityPresenter;
import io.havoc.todo.view.MainActivityView;

public class MainActivity extends TiActivity<MainActivityPresenter, MainActivityView> implements MainActivityView {

    //    private static final String FRAGMENT_LIST_VIEW = "List view";
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
        this.startActivity(newTaskIntent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fabNewTask.setOnClickListener(view -> getPresenter().newTaskButtonClicked());

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new ListFragment(), FRAGMENT_LIST_VIEW)
//                    .commit();
//        }
    }
}
