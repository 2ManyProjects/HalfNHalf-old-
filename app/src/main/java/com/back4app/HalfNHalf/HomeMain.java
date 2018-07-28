package com.back4app.HalfNHalf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HomeMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public Profile UserProfile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        serverQuery();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //launchActivity("MapTest");
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void serverQuery()
    {
        ParseQuery<Profile> query = ParseQuery.getQuery("Profile");
        //query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Profile>() {
            @Override
            public void done(List<Profile> list, @Nullable ParseException e) {
                if (e == null){
                    UserProfile = list.get(0);
                    list.get(0).addStore("TEST STORE");
                    list.get(0).storeList.get(0).addDeal(0.5, "Not on Smartwatches", 4);
                    alertDisplayer("", UserProfile.getEmail());
                    alertDisplayer("", list.get(0).storeList.get(0).getID());
                    alertDisplayer("", list.get(0).storeList.get(0).storeDeals.get(0).getRate() + " " + list.get(0).storeList.get(0).storeDeals.get(0).getAmount() + " " + list.get(0).storeList.get(0).storeDeals.get(0).getWarning());
                }else if(checkConnection()){
                    alertDisplayer("Error", "Network issues :P");
                }
            }
        });
    }

/*
    private void serverQuery()
    {
        ParseQuery<Profile> query = ParseQuery.getQuery("Profile");
        //query.orderByAscending("createdAt");
        query.getInBackground("userProfile", new GetCallback<Profile>() {
            public void done(Profile profile, @Nullable ParseException e) {
                if (e == null){
                    UserProfile = profile;
                    UserProfile.addStore("TEST STORE");
                    UserProfile.storeList.get(0).addDeal(0.5, "Not on Smartwatches", 4);
                    alertDisplayer(UserProfile.getEmail(), UserProfile.getUsername());
                    alertDisplayer(UserProfile.storeList.get(0).getID(), UserProfile.storeList.get(0).storeDeals.get(0).getRate() + " " + UserProfile.storeList.get(0).storeDeals.get(0).getAmount() + " " + UserProfile.storeList.get(0).storeDeals.get(0).getWarning());
                }else if(checkConnection()){
                    alertDisplayer("Error", "Network issues :P");
                }
            }
        });
    }*/


    public boolean checkConnection(){   //method for checking network connection
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeMain.this)
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
