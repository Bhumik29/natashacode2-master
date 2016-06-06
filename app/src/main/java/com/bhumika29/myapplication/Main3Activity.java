package com.bhumika29.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.buddy.sdk.Buddy;
import com.buddy.sdk.BuddyCallback;
import com.buddy.sdk.BuddyClient;
import com.buddy.sdk.BuddyResult;
import com.buddy.sdk.models.User;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Main3Activity extends AppCompatActivity {

    private GoogleApiClient client;

    Button mButton;
    EditText username;
    EditText password;
    EditText name;
    private BuddyClient init;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Context myContext = getApplicationContext(); // If there is no context, set myContext to null
        init = Buddy.init(getApplicationContext(), "bbbbbc.plvnwCtmFqFhc", "f2f7c6c8-fbce-5ca5-4dc3-b98aa661755b");

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void signup(View view) {

        name = (EditText) findViewById(R.id.editText);
        username = (EditText) findViewById(R.id.editText4);
        password = (EditText) findViewById(R.id.editText5);

        System.out.println("name:" + name.getText().toString());
        System.out.println("user name:" + username.getText().toString());
        System.out.println("password:" + password.getText().toString());

        init.createUser(username.getText().toString(), password.getText().toString(), name.getText().toString(), null, null, null, null, null, new BuddyCallback<User>(User.class) {
            public void completed(BuddyResult<User> result) {
                if (result.getIsSuccess()) {
                    System.out.println("User created.");
                    startActivity(new Intent(Main3Activity.this, Main2Activity.class));
                } else {
                    System.out.println("Create user failed.");
                    startActivity(new Intent(Main3Activity.this, MainActivity.class));
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main3 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://com.bhumika29.fbladress/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main3 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.bhumika29.fbladress/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
