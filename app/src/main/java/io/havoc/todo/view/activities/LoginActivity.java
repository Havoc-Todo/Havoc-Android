package io.havoc.todo.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import net.grandcentrix.thirtyinch.TiActivity;

import io.havoc.todo.R;
import io.havoc.todo.presenter.LoginActivityPresenter;
import io.havoc.todo.view.LoginActivityView;

public class LoginActivity extends TiActivity<LoginActivityPresenter, LoginActivityView>
        implements LoginActivityView {

    @NonNull
    @Override
    public LoginActivityPresenter providePresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
