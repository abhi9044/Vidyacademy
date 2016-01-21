package com.holamed.meddapp;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.lang.ref.WeakReference;
import java.util.Observable;

/**
 * Created by Era on 9/4/2015.
 */
public class GoogleConnection extends Observable
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_CODE = 1234;
    public SharedPreferences loginPrefs;
    public SharedPreferences.Editor loginEditor;

    public void connect() {
        currentState.connect(this);
    }

    public void disconnect() {
        currentState.disconnect(this);
    }

    public void revokeAccessAndDisconnect() {
        currentState.revokeAccessAndDisconnect(this);
    }

    public static GoogleConnection getInstance(Activity activity) {
        if (null == sGoogleConnection) {
            sGoogleConnection = new GoogleConnection(activity);
        }

        return sGoogleConnection;
    }

    @Override
    public void onConnected(Bundle hint) {
        changeState(State.OPENED);

    }

    private void getInfo()
    {
        Log.d("google","profileInfo");
        try {
            Log.d("google","try");
            if (Plus.PeopleApi.getCurrentPerson(googleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(googleApiClient);
                String personName = currentPerson.getDisplayName();
                Log.d("google",personName);
                int personGender=currentPerson.getGender();
                Log.d("google", String.valueOf(personGender));
                String personPhotoUrl = currentPerson.getImage().getUrl();
                Log.d("google",personPhotoUrl);
                String personEmail = Plus.AccountApi.getAccountName(googleApiClient);
                Log.d("google",personEmail);
                   /* loginPrefs = this.getSharedPreferences("MeddLoginDetails", Context.MODE_PRIVATE);
                    loginEditor = loginPrefs.edit();

                    loginEditor.putString("gender",gender);
                    loginEditor.putString("name",personName);
                    loginEditor.putString("email",personEmail);
                    loginEditor.putString("userId","none");
                    loginEditor.putBoolean("saved", true);
                    loginEditor.putString("platform","google");
                    Log.d("checkfb name", String.valueOf(loginPrefs.getString("name", "none")));
                    loginEditor.commit();
*/
               /* username.setText(personName);
                emailLabel.setText(email);
                new LoadProfileImage(image).execute(personPhotoUrl);
               */ // update profile frame with new info about Google Account
                // profile

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("googlecatch", String.valueOf(e));
        }


    }
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        // We call connect() to attempt to re-establish the connection or get a
        // ConnectionResult that we can attempt to resolve.
        changeState(State.CLOSED);
        connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (currentState.equals(State.CLOSED) && connectionResult.hasResolution()) {
            changeState(State.CREATED);
            this.connectionResult = connectionResult;
        } else {
            connect();
        }
    }

    public void onActivityResult(int result) {
        if (result == Activity.RESULT_OK) {
            // If the error resolution was successful we should continue
            // processing errors.
            changeState(State.CREATED);
        } else {
            // If the error resolution was not successful or the user canceled,
            // we should stop processing errors.
            changeState(State.CLOSED);
        }

        // If Google Play services resolved the issue with a dialog then
        // onStart is not called so we need to re-attempt connection here.
        onSignIn();
    }

    public String getAccountName() {
        return Plus.AccountApi.getAccountName(googleApiClient);
    }
    public String getPerson() {
        googleApiClient.connect();
       Person p= Plus.PeopleApi.getCurrentPerson(googleApiClient);
        Log.d("google name is", String.valueOf(p.getName()));
        return String.valueOf(p);
    }

    protected GoogleApiClient onSignIn() {
        if (!googleApiClient.isConnected() && !googleApiClient.isConnecting()) {
            googleApiClient.connect();

        }
        Log.d("googlereturn", String.valueOf(googleApiClient));
        return googleApiClient;
    }


    protected void onSignOut() {
        if (googleApiClient.isConnected()) {
            // We clear the default account on sign out so that Google Play
            // services will not return an onConnected callback without user
            // interaction.
            Plus.AccountApi.clearDefaultAccount(googleApiClient);
            googleApiClient.disconnect();
            googleApiClient.connect();
            changeState(State.CLOSED);
        }
    }

    protected void onSignUp() {
        // We have an intent which will allow our user to sign in or
        // resolve an error.  For example if the user needs to
        // select an account to sign in with, or if they need to consent
        // to the permissions your app is requesting.

        try {
            // Send the pending intent that we stored on the most recent
            // OnConnectionFailed callback.  This will allow the user to
            // resolve the error currently preventing our connection to
            // Google Play services.
            changeState(State.OPENING);
            connectionResult.startResolutionForResult(activityWeakReference.get(), REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            // The intent was canceled before it was sent.  Attempt to connect to
            // get an updated ConnectionResult.
            changeState(State.CREATED);
            googleApiClient.connect();
        }
    }

    protected void onRevokeAccessAndDisconnect() {
        // After we revoke permissions for the user with a GoogleApiClient
        // instance, we must discard it and create a new one.
        Plus.AccountApi.clearDefaultAccount(googleApiClient);

        // Our sample has caches no user data from Google+, however we
        // would normally register a callback on revokeAccessAndDisconnect
        // to delete user data so that we comply with Google developer
        // policies.
        Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient);
        googleApiClient = googleApiClientBuilder.build();
        googleApiClient.connect();
        changeState(State.CLOSED);
    }

    private GoogleConnection(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);

        googleApiClientBuilder =
                new GoogleApiClient.Builder(activityWeakReference.get().getApplicationContext())
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(Plus.API, Plus.PlusOptions.builder().build())
                        .addScope(new Scope("email"));

        googleApiClient = googleApiClientBuilder.build();
        currentState = State.CLOSED;
    }

    private void changeState(State state) {
        currentState = state;
        setChanged();
        notifyObservers(state);
    }

    private static GoogleConnection sGoogleConnection;

    private WeakReference<Activity> activityWeakReference;
    private GoogleApiClient.Builder googleApiClientBuilder;
    private GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;
    private State currentState;

}
