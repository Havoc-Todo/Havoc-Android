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
import butterknife.ButterKnife;
import io.havoc.todo.R;
import io.havoc.todo.presenter.LoginActivityPresenter;
import io.havoc.todo.util.LogUtil;
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
            handleGoogleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    @Override
    public void handleGoogleSignInResult(GoogleSignInResult result) {
        LogUtil.d("handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();

            if (account != null) {
                getPresenter().handleSuccessGoogleAuth(this, account);
            }

            startActivity(new Intent(this, MainActivity.class)); //go to the MainActivity
            finish();
        } else {
            getPresenter().wipeAuthSettings(this);
        }
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
        ButterKnife.bind(this);

        mGoogleApiClient = getPresenter().configureGoogleApiClient(this, this, this);

        //Set the Google Sign-In button size
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);
    }
}
