package com.example.a229zzg.nusanswers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UserHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabHome;
    TabItem tabContributions;
    TabItem tabPastModules;
    TabItem tabSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                // Contributions tab
                if (tab.getPosition() == 1) {
                    // Change Icon to Selected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.contribution_icon));
                    // Change Title
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getResources().
                                getString(R.string.contributions_title));
                    }
                }
                // Past Modules tab
                else if (tab.getPosition() == 2) {
                    // Change Icon to Selected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.clock_icon));
                    // Change Title
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getResources().
                                getString(R.string.pastmodules_title));
                    }
                }
                // Search tab
                else if (tab.getPosition() == 3) {
                    // Change Icon to Selected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.search_icon));
                    // Change Title
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getResources().
                                getString(R.string.search_title));
                    }
                } else {
                    // Change Icon to Selected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.home_icon));
                    // Change Title
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(getResources().
                                getString(R.string.home_title));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Contributions tab
                if (tab.getPosition() == 1) {
                    // Change Icon to Unselected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.contribution_icon_black));
                }
                // Past Modules tab
                else if (tab.getPosition() == 2) {
                    // Change Icon to Unselected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.clock_icon_black));
                }
                // Search tab
                else if (tab.getPosition() == 3) {
                    // Change Icon to Unselected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.search_icon_black));
                } else {
                    // Change Icon to Unelected
                    tabLayout.getTabAt(tab.getPosition()).setIcon(getResources().
                            getDrawable(R.drawable.home_icon_black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
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
    */

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
