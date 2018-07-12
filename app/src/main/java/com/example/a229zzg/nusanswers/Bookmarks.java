package com.example.a229zzg.nusanswers;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Bookmarks extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserInfo");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
    final int radius = 50;
    final int margin = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Collapsing toolbar on change
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                TextView title = findViewById(R.id.bookmark_title);
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    // Collapsed
                    title.setPadding(0, 0, 0, 40);
                }
                else
                {
                    // Expanded
                    title.setPadding(0, 0, 0, 0);
                }
            }
        });

        final ImageView userProfilePicture = findViewById(R.id.bookmark_user_picture);
        if (firebaseUser != null) {
            String id = mAuth.getCurrentUser().getUid();
            storageReference.child(id).child("Images/Profile Picture").
                    getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().
                            transform(new RoundTransformation(radius, margin)).into(userProfilePicture);
                }
            });
        }
    }
}
