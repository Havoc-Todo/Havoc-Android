package io.havoc.todo.presenter;


import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.havoc.todo.view.LoginActivityView;

public class LoginActivityPresenter extends TiPresenter<LoginActivityView> {
    /**
     * Configures the Google Sign-in options
     *
     * @return the GoogleSignInOptions
     */
    public GoogleSignInOptions configureGoogleSignIn() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    public void signInToGoogle() {
        Intent
    }

}
