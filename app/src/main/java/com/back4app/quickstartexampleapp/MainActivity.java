package com.back4app.quickstartexampleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;

    public void redirectUser(){
        if(ParseUser.getCurrentUser()!=null){
            Intent intent = new Intent(getApplicationContext(),UsersActivity.class);
            startActivity(intent);
        }
    }

    public void singupLogin(View view){
        if(TextUtils.isEmpty(usernameEditText.getText())||TextUtils.isEmpty(passwordEditText.getText())){
            Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
        }else {
            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e==null){
                        //Logged in
                        redirectUser();
                    }else{
                        ParseUser newUser = new ParseUser();
                        newUser.setUsername(usernameEditText.getText().toString());
                        newUser.setPassword(passwordEditText.getText().toString());
                        newUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    //Signed in
                                    redirectUser();
                                }else{
                                    Toast.makeText(MainActivity.this, "Unable to log in", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        redirectUser();
    }

}
