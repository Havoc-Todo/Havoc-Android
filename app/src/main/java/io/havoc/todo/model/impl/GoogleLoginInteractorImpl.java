package me.nxt3.havoc.model.impl;

import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import me.nxt3.havoc.model.ILoginInteractor;
import me.nxt3.havoc.util.LogUtil;

public class GoogleLoginInteractorImpl implements ILoginInteractor, GoogleApiClient.OnConnectionFailedListener {

    /**
     * Builds GoogleSignInOptions
     *
     * @return a newly created GoogleSignInOptions
     */
    private GoogleSignInOptions buildGoogleSignInOptions() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    /**
     * Builds a GoogleAPIClient
     * Make sure to create new after revoking access or for first time sign in
     *
     * @return a newly created GoogleApiClient
     */
    private GoogleApiClient buildGoogleAPIClient() {
        return new GoogleApiClient.Builder()
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, buildGoogleSignInOptions())
                .build();
    }

    @Override
    public boolean login(String username, String password) {
        //TODO, implement login()
        return false;
    }

    @Override
    public boolean logout() {
        //TODO, implement logout()
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        LogUtil.e("onConnectionFailed: " + connectionResult);
    }
}
