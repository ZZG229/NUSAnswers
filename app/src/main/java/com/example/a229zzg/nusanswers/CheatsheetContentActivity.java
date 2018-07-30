package com.example.a229zzg.nusanswers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
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

public class CheatsheetContentActivity extends AppCompatActivity {

    private Intent intent;
    private String code;
    private String filter;
    private String year;
    private String sem;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheatsheet_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserInfo");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        final int radius = 50;
        final int margin = 5;

        // Intent
        intent = getIntent();
        code = intent.getStringExtra("moduleCode");
        filter = intent.getStringExtra("filter");
        year = intent.getStringExtra("academicYear");
        sem = intent.getStringExtra("semester");
        id = intent.getStringExtra("uid");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // allow user to download cheatsheet
                // yet to implement
                Toast.makeText(getApplicationContext(),"Feature yet to be implemented",Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton close = findViewById(R.id.cheat_close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Loading related information onto App bar
        final ImageView userProfilePicture = findViewById(R.id.profile_pic_cheat);
        if (firebaseUser != null) {
            storageReference.child(id).child("Images/Profile Picture").
                    getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().
                            transform(new RoundTransformation(radius,margin)).into(userProfilePicture);
                }
            });
            DatabaseReference ref = databaseReference.child(id);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TextView userName = findViewById(R.id.user_name_cheat);
                    if (dataSnapshot.hasChild("username")) {
                        String displayName = (String) dataSnapshot.child("username").getValue();
                        userName.setText(displayName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        final ImageView cheatSheet = findViewById(R.id.uploaded_cheatsheet);
        if (firebaseUser != null) {
            FirebaseStorage.getInstance().getReference("UserContributions").child(code).child(filter).child(year).child(sem).child(id)
                    .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(cheatSheet);
                }
            });
        }

    }

}
