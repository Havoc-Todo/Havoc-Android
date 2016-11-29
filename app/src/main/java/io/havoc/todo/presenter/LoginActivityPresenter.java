package io.havoc.todo.presenter;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import net.grandcentrix.thirtyinch.TiPresenter;

import io.havoc.todo.model.PrefKey;
import io.havoc.todo.util.prefs.AuthSharedPrefs;
import io.havoc.todo.view.LoginActivityView;

public class LoginActivityPresenter extends TiPresenter<LoginActivityView> {
    /**
     * Configures the Google Sign-in options
     *
     * @return the GoogleSignInOptions
     */
    private GoogleSignInOptions configureGoogleSignIn() {
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    /**
     * Configures the GoogleApiClient
     *
     * @param context  for the GoogleApiClient
     * @param listener OnConnectionFailedListener that was implemented
     * @param activity calling activity
     * @return the configured GoogleApiClient
     */
    public GoogleApiClient configureGoogleApiClient(Context context, GoogleApiClient.OnConnectionFailedListener listener, AppCompatActivity activity) {
        return new GoogleApiClient.Builder(context)
                .enableAutoManage(activity, listener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, configureGoogleSignIn())
                .build();
    }

    /**
     * Wipes Auth settings if the attempt to login failed (not sure why I do this, but might as well)
     *
     * @param context for accessing SharedPrefs
     */
    public void wipeAuthSettings(Context context) {
        AuthSharedPrefs.getInstance(context).put(PrefKey.IS_AUTH, false);
        AuthSharedPrefs.getInstance(context).remove(PrefKey.GOOGLE_USER_EMAIL);
        AuthSharedPrefs.getInstance(context).remove(PrefKey.USER_ID);
    }

    /**
     * Handles storing the email and userID after a successful auth with Google
     *
     * @param context for accessing SharedPrefs
     * @param account the user's Google account used to access info
     */
    public void handleSuccessGoogleAuth(Context context, GoogleSignInAccount account) {
        final String email = account.getEmail();
        AuthSharedPrefs.getInstance(context).put(PrefKey.GOOGLE_USER_EMAIL, email);

        assert email != null;
        //userID is in the format of user-host
        final String userID = email.replace("@", "-").substring(0, email.length() - 4);
        AuthSharedPrefs.getInstance(context).put(PrefKey.USER_ID, userID);

        AuthSharedPrefs.getInstance(context).put(PrefKey.IS_AUTH, true);
    }

}
