package io.havoc.todo.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import net.grandcentrix.thirtyinch.TiActivity;

import io.havoc.todo.R;
import io.havoc.todo.adapters.TaskListAdapter;
import io.havoc.todo.model.impl.AbstractTaskProviderImpl;
import io.havoc.todo.presenter.MainActivityPresenter;
import io.havoc.todo.view.MainActivityView;

public class MainActivity extends TiActivity<MainActivityPresenter, MainActivityView> implements MainActivityView {

    private RecyclerView mTaskList;

    @NonNull
    @Override
    public MainActivityPresenter providePresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public void showList() {
        AbstractTaskProviderImpl data = new AbstractTaskProviderImpl();
        mTaskList.setAdapter(new TaskListAdapter(data));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskList = (RecyclerView) findViewById(R.id.task_list_rv);
    }
}
