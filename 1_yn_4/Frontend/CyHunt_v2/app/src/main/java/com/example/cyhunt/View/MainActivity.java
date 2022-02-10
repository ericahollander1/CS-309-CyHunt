package com.example.cyhunt.View;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.cyhunt.Presenter.MainPresenter;
import com.example.cyhunt.R;
import com.google.android.material.navigation.NavigationView;


/**
 * The Main activity that hosts all of the navigation drawer fragments. Handles Android related code.
 * @author Lexi
 */
public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private NavController navController;
    private MainPresenter presenter;
    private SharedPreferences sharedPreferences;
    private final int ACHIEVEMENT_CREATE_ACCOUNT = 1;
    private final int ACHIEVEMENT_FIRST_FOUND_CY = 2;
    private final int ACHIEVEMENT_FIRST_TRIVIA_CORRECT = 3;
    private final int ACHIEVEMENT_25_POINTS = 4;
    private final int ACHIEVEMENT_50_POINTS = 5;
    private final int ACHIEVEMENT_75_POINTS = 6;
    private final int ACHIEVEMENT_FIRST_TRIVIA_PROPOSED = 5;
    private final int ACHIEVEMENT_FIRST_TRIVIA_APPROVED = 6;

    /**
     * Creates the activity
     * @param savedInstanceState a savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("com.example.cyhunt.PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        presenter = new MainPresenter(this, getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //Update score display in nav header
                presenter.loadCyscore(sharedPreferences.getInt("USER_ID", -1));
                TextView header = navigationView.getHeaderView(0).findViewById(R.id.header_user_score);
                header.setText("Score: " + Integer.toString(sharedPreferences.getInt("USER_SCORE", -1)));
                if ((sharedPreferences.getInt("ACHIEVEMENT_25_POINTS", -1) == -1) && (sharedPreferences.getInt("USER_SCORE", -1) > 24)) {
                    presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_25_POINTS);
                    editor.putInt("ACHIEVEMENT_25_POINTS", 1);
                    editor.apply();
                }
                if ((sharedPreferences.getInt("ACHIEVEMENT_50_POINTS", -1) == -1) && (sharedPreferences.getInt("USER_SCORE", -1) > 49)) {
                    presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_50_POINTS);
                    editor.putInt("ACHIEVEMENT_50_POINTS", 1);
                    editor.apply();
                }
                if ((sharedPreferences.getInt("ACHIEVEMENT_75_POINTS", -1) == -1) && (sharedPreferences.getInt("USER_SCORE", -1) > 24)) {
                    presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_75_POINTS);
                    editor.putInt("ACHIEVEMENT_75_POINTS", 1);
                    editor.apply();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }


    /**
     * Finishes up some UI stuff post creation of the activity
     * @param savedInstanceState a savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        navController = (NavController) Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        drawerToggle.syncState();

        //go to login screen if necessary
        checkUser();


    }

    /**
     * Allows navigation with the navigation drawer
     * @param item a MenuItem
     * @return the selected MenuItem
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Locks the navigation drawer so that the user cannot access it.
     */
    @Override
    public void lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    /**
     * Unlocks the navigation drawer so that the user can access it.
     */
    @Override
    public void unlockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /**
     * Takes the user to the map page after pressing a button on the landing page
     */
    @Override
    public void onFindCyButtonClicked() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        //setContentView(R.layout.activity_maps);

        //navController.navigate((R.id.action_nav_landing_page_to_nav_map));
    }

    /**
     * Included for the button onClickListener
     * @param view a View
     */
    @Override
    public void onClick(final View view) {}

    /**
     * Updates the user's score inside shared preferences
     * @param s the user's new score
     */
    @Override
    public void setScore(String s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_SCORE", parseInt(s));
        editor.apply();
    }

    //Checks that the user has logged in
    //Fills the nav drawer info accordingly, if necessary
    private void checkUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //if USER_ID is -1, it implies that no user has logged in or selected play as guest
        if (sharedPreferences.getInt("USER_ID", -1) == -1) {
            Intent newIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(newIntent);
        } else {
            navigationView.getMenu().findItem(R.id.loginActivity).setTitle("Login");
            TextView header1 = navigationView.getHeaderView(0).findViewById(R.id.header_username);
            TextView header2 = navigationView.getHeaderView(0).findViewById(R.id.header_user_score);
            presenter.loadCyscore(sharedPreferences.getInt("USER_ID", -1));
            header1.setText(sharedPreferences.getString("USERNAME", "Guest User"));
            header2.setText("Score: " + Integer.toString(sharedPreferences.getInt("USER_SCORE",-1)));
            if (sharedPreferences.getString("USER_ROLE", "GUEST").equals("USER")) {
                //hide nav menu links for some items
                //navigationView.getMenu().findItem(R.id.nav_dashboard).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_achievements).setVisible(true);
                navigationView.getMenu().findItem(R.id.loginActivity).setTitle("Logout");
                presenter.loadAchievements(sharedPreferences.getInt("USER_ID", -1));
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_ACCOUNT", -1) != 1) {
                    presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_CREATE_ACCOUNT);
                    editor.putInt("ACHIEVEMENT_CREATE_ACCOUNT", 1);
                    editor.apply();
                }
            } else if (sharedPreferences.getString("USER_ROLE", "GUEST").equals("COLLABORATOR")) {
                //navigationView.getMenu().findItem(R.id.nav_dashboard).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_achievements).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_proposeTrivia).setVisible(true);
                navigationView.getMenu().findItem(R.id.loginActivity).setTitle("Logout");
                navigationView.getMenu().findItem(R.id.nav_collaborator_chatroom).setVisible(true);
                presenter.loadAchievements(sharedPreferences.getInt("USER_ID", -1));
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_ACCOUNT", -1) != 1) {
                    presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_CREATE_ACCOUNT);
                    editor.putInt("ACHIEVEMENT_CREATE_ACCOUNT", 1);
                    editor.apply();
                }
                //checkAuthoredTrivia();
            } else if (sharedPreferences.getString("USER_ROLE", "GUEST").equals("ADMIN")) {
                //navigationView.getMenu().findItem(R.id.nav_dashboard).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_achievements).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_proposeTrivia).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_approveTrivia).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_createTrivia).setVisible(true);
                navigationView.getMenu().findItem(R.id.loginActivity).setTitle("Logout");
                navigationView.getMenu().findItem(R.id.nav_collaborator_chatroom).setVisible(true);
                presenter.loadAchievements(sharedPreferences.getInt("USER_ID", -1));
                if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_ACCOUNT", -1) != 1) {
                    presenter.updateAchievements(sharedPreferences.getInt("USER_ID", -1), ACHIEVEMENT_CREATE_ACCOUNT);
                    editor.putInt("ACHIEVEMENT_CREATE_ACCOUNT", 1);
                    editor.apply();
                }
                //checkAuthoredTrivia();
            }
        }
    }

    private void checkAuthoredTrivia() {
        if (sharedPreferences.getInt("ACHIEVEMENT_CREATE_TRIVIA_1", -1) != 1 ) {
            //check for authored trivia
        }
    }

    /**
     * Adds a completed achievement to the user's shared preferences
     * This is triggered when a user logs in either for the first time
     * or after logging out and back in to ensure that shared preferences is up to date
     * @param i the achievement ID
     */
    @Override
    public void checkAchievements(int i) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch(i) {
            case 1:
                editor.putInt("ACHIEVEMENT_CREATE_ACCOUNT", 1);
                editor.apply();
                break;
            case 2:
                editor.putInt("ACHIEVEMENT_FIRST_FOUND_CY", 1);
                editor.apply();
                break;
            case 3:
                editor.putInt("ACHIEVEMENT_FIRST_TRIVIA_CORRECT", 1);
                editor.apply();
                break;
            case 4:
                editor.putInt("ACHIEVEMENT_25_POINTS", 1);
                editor.apply();
                break;
            case 5:
                editor.putInt("ACHIEVEMENT_50_POINTS", 1);
                editor.apply();
                break;
            case 6:
                editor.putInt("ACHIEVEMENT_75_POINTS", 1);
                editor.apply();
                break;
            //case 5:
            //    editor.putInt("ACHIEVEMENT_FIRST_TRIVIA_PROPOSED", 1);
            //    editor.apply();
            //    break;
            //case 6:
            //    editor.putInt("ACHIEVEMENT_FIRST_TRIVIA_APPROVED", 1);
            //    editor.apply();
            //    break;
            default: break;
        }
    }
}