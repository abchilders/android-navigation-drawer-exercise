package com.codepath.android.navigationdrawerexercise.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.codepath.android.navigationdrawerexercise.R;
import com.codepath.android.navigationdrawerexercise.fragments.FamilyGuyFragment;
import com.codepath.android.navigationdrawerexercise.fragments.FuturamaFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SimpsonsFragment;
import com.codepath.android.navigationdrawerexercise.fragments.SouthParkFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Set the Toolbar to act as the ActionBar for this Activity window
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Displays an Up icon (<-), to be replaced with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Set up toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find and set up drawer view
        nvDrawer = findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        // manually display the first fragment by default on initial load
        nvDrawer.getMenu().getItem(0).setChecked(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new FamilyGuyFragment()).commit();
        setTitle(R.string.family_guy); 
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Log.i("MainActivity", "menu item id is " + menuItem.getItemId());
        Class fragmentClass;
        // choose what to assign to fragmentClass based on the menu item selected
        switch(menuItem.getItemId()) {
            case R.id.nav_family_guy:
                fragmentClass = FamilyGuyFragment.class;
                break;
            case R.id.nav_futurama:
                fragmentClass = FuturamaFragment.class;
                break;
            case R.id.nav_simpsons:
                fragmentClass = SimpsonsFragment.class;
                break;
            case R.id.nav_south_park:
                fragmentClass = SouthParkFragment.class;
                break;
            default:
                fragmentClass = FamilyGuyFragment.class;
        }

        // then set fragment = the assigned fragment class
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            Log.i("MainActivity", fragmentClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceSate has occurred
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // Called when the user taps on hamburger menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled the
        // nav drawer indicator touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: must pass in a valid toolbar reference to render hamburger menu
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }
}
