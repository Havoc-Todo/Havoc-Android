package io.havoc.todo.view;


import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import net.grandcentrix.thirtyinch.TiView;

public interface LoginActivityView extends TiView {

    /**
     * Handles signing into Google
     * Starts the Sign In Intent
     */
    void signInToGoogle();

    /**
     * Handles the result from logging in
     *
     * @param result from signing in to Google
     */
    void handleGoogleSignInResult(GoogleSignInResult result);
}
