package com.back4app.HalfNHalf;

//Front End
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//Parse dependencies
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
//import com.parse.ParseBroadcastReceiver;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button Login, Register;
    EditText username, password;

    Button facebookLogin;
    Button twitterLogin;
    Button cloudCode;

    ProgressDialog progressDialog;
    final List<String> permissions = Arrays.asList("public_profile", "email");

    TextView t_username;
    TextView t_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);
        setContentView(R.layout.activity_main);
        ParseUser user = new ParseUser();
        progressDialog = new ProgressDialog(MainActivity.this);

        Login = (Button)findViewById(R.id.login);
        Register = (Button)findViewById(R.id.register);

        username = (EditText)findViewById(R.id.usernamestr);
        password = (EditText)findViewById(R.id.passwordstr);
        Login.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v)
            {
                progressDialog.setMessage("Please Wait");
                progressDialog.setTitle("Logging in");
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            parseLogin();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });



        Register.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   launchActivity("Register");
                                               }
                                           });


        // Save the current Installation to Back4App
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
    public void launchActivity(String activity) {
        Intent intent;
        switch(activity) {
            case "Register":
                intent = new Intent(this, RegisterUser.class);
                startActivity(intent);
                break;
            case "Login":
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            break;
            case "Home":
                intent = new Intent(this, HomeMain.class);
                startActivity(intent);
            break;
            case "MapTest":
                intent = new Intent(this, MapsTest.class);
                startActivity(intent);
            break;
            default: alertDisplayer("Missing Activity", "Activity not found");
            break;
        }
    }


    void alertDisplayer(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
    public String getUsername()
    {
        return username.getText().toString();
    }
    void parseLogin(){
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    progressDialog.dismiss();
                    if(parseUser.getBoolean("emailVerified")) {
                        //getUserDetailFromParse();
                        launchActivity("Home");
                        alertDisplayer("Login success", "");
                    }
                    else {
                        parseUser.logOut();
                        alertDisplayer("Login Fail", "Please Verify Your Email first");
                    }
                } else {
                    progressDialog.dismiss();
                    alertDisplayer("Login fail", e.getMessage()+ "Please retry");
                }
            }
        });
    }

}