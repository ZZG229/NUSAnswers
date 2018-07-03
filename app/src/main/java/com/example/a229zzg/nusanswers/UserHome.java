package com.example.a229zzg.nusanswers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
    final int radius = 300;
    final int margin = 15;

    //FIREBASE
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserInfo");
    DatabaseReference databaseReference = mfirebaseDatabase.getReference("UserInfo");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.home_title));
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                final ImageView userPicture = findViewById(R.id.nav_profile_picture);
                TextView userName = findViewById(R.id.nav_user_name);
                if (firebaseUser != null) {
                    String id = firebaseUser.getUid();
                    storageReference.child(id).child("Images/Profile Picture").
                            getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).fit().centerCrop().
                                    transform(new RoundTransformation(radius,margin)).into(userPicture);
                        }
                    });

                    if (firebaseUser.getDisplayName() != null) {
                        userName.setText(firebaseUser.getDisplayName());
                    }
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /* Create menu
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

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, UserProfile.class));
        } else if (id == R.id.nav_bookmarks) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
