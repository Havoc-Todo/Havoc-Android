package io.havoc.todo.view;

import android.os.Bundle;
import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiActivity;

import io.havoc.todo.R;
import io.havoc.todo.fragments.ListFragment;
import io.havoc.todo.presenter.MainActivityPresenter;

public class MainActivity extends TiActivity<MainActivityPresenter, MainActivityView> implements MainActivityView {

    private static final String FRAGMENT_LIST_VIEW = "List view";

    @NonNull
    @Override
    public MainActivityPresenter providePresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }
    }
}
