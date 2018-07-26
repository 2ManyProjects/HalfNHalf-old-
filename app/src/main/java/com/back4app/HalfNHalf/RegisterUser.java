package com.back4app.HalfNHalf;

//Front End
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUser extends Activity {
    private Button Register, Back;
    private EditText username, password;
    private AutoCompleteTextView email;

    ProgressDialog progressDialog;
    final List<String> permissions = Arrays.asList("public_profile", "email");

    // UI references.

    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this);
        setContentView(R.layout.activity_register_user);
        ParseUser user = new ParseUser();
        progressDialog = new ProgressDialog(RegisterUser.this);

        Register = (Button)findViewById(R.id.reg);
        Back = (Button)findViewById(R.id.back);

        email = (AutoCompleteTextView) findViewById(R.id.emailstr);
        username = (EditText)findViewById(R.id.usernamestr);
        password = (EditText)findViewById(R.id.passwordstr);




        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attemptRegister();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
/*
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });*/
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity("Login");
            }
        });
        // Save the current Installation to Back4App
       // ParseInstallation.getCurrentInstallation().saveInBackground();

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
    private void attemptRegister() {
        // Reset errors.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String emailstr = email.getText().toString();
        String usernamestr = username.getText().toString();
        String passwordstr = password.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordstr) && !isPasswordValid(passwordstr)) {
            password.setError("Passwords must be atleast 6 characters long with a Capital, number and special character, Passphrases need to be atleast 12 characters long");
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailstr)) {
            email.setError("Please enter and Email Address");
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailstr)) {
            email.setError("Invalid Email");
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            ParseUser user = new ParseUser();
            Profile profile = new Profile();
            profile.setEmail(emailstr);
            profile.setUsername(usernamestr);
            user.setUsername(usernamestr);
            user.setPassword(passwordstr);
            user.setEmail(emailstr);
            profile.saveInBackground(new SaveCallback() {
                                         @Override
                                         public void done(ParseException e) {
                                             if (progressDialog.isShowing())
                                                 progressDialog.dismiss();
                                             if (e == null) {
                                                 ParseQuery<ParseObject> query = ParseQuery.getQuery("Product");
                                                 query.orderByDescending("updatedAt");
                                                 query.getFirstInBackground(new GetCallback<ParseObject>() {
                                                     public void done(ParseObject object, ParseException e) {

                                                         if (object == null) {
                                                             Log.d("product", "The getFirst request failed.");
                                                         } else {
                                                             Intent i = new Intent(RegisterUser.this, Profile.class);
                                                             i.putExtra("email", object.getString("email"));
                                                             i.putExtra("username", object.getString("username"));
                                                             startActivity(i);
                                                             finish();
                                                         }
                                                     }
                                                 });


                                                 Toast.makeText(RegisterUser.this, "Profile Has been Submitted.", Toast.LENGTH_LONG).show();
                                             } else if (e.getCode() == ParseException.CONNECTION_FAILED) {
                                                 Toast.makeText(RegisterUser.this, "No internet Connection please check your connection!",
                                                         Toast.LENGTH_LONG).show();

                                             } else {
                                                 Toast.makeText(RegisterUser.this, "Error:" + e.getMessage(),
                                                         Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     });
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        final String title = "Account Created Successfully!";
                        final String message = "Please verify your email before Login";

                        alertDisplayer(title, message);
                    } else {
                        final String title = "Error Account Creation failed";
                        final String message = "Account could not be created";

                        alertDisplayer(title, message + " :" + e.getMessage());
                    }
                }
            });
            //finish();
        }
    }

    void alertDisplayer(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUser.this)
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

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String newPassword) {
        if(newPassword.length() >= 12)
            return true;
        return newPassword.length()>= 6 && isValidPass(newPassword);
    }

    //*****************************************************************
    public static boolean isValidPass(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

}
