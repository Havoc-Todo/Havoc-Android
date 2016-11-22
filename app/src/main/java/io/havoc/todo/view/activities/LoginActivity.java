package io.havoc.todo.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import net.grandcentrix.thirtyinch.TiActivity;

import butterknife.BindView;
import io.havoc.todo.R;
import io.havoc.todo.model.PrefKey;
import io.havoc.todo.presenter.LoginActivityPresenter;
import io.havoc.todo.util.LogUtil;
import io.havoc.todo.util.prefs.AuthSharedPrefs;
import io.havoc.todo.view.LoginActivityView;

public class LoginActivity extends TiActivity<LoginActivityPresenter, LoginActivityView>
        implements LoginActivityView, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private final int RC_SIGN_IN = 9001;
    @BindView(R.id.google_sign_in_button)
    public SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;

    @NonNull
    @Override
    public LoginActivityPresenter providePresenter() {
        return new LoginActivityPresenter();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.isSuccess()) {
            LogUtil.e("Error logging in.");
        } else {
            LogUtil.v("Success logging in!");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_sign_in_button:
                signInToGoogle();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    @Override
    public void handleGoogleSignInResult(GoogleSignInResult result) {
        LogUtil.d("handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            if (account != null) {
                AuthSharedPrefs.getInstance(this).put(PrefKey.GOOGLE_USER_EMAIL, account.getEmail());
            }

            AuthSharedPrefs.getInstance(this).put(PrefKey.IS_AUTH, true);
        } else {
            AuthSharedPrefs.getInstance(this).put(PrefKey.IS_AUTH, false);
            AuthSharedPrefs.getInstance(this).remove(PrefKey.GOOGLE_USER_EMAIL);
        }
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = result.getSignInAccount();
//        } else {
//            LogUtil.d("handleSignInResult:" + result.isSuccess());
//        }
    }

    @Override
    public void signInToGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, getPresenter().configureGoogleSignIn())
                .build();

        //Set the Google Sign-In button size
        signInButton.setSize(SignInButton.SIZE_WIDE);
    }
}
