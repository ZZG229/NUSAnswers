package com.example.a229zzg.nusanswers;

import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

public class UserProfile extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserInfo");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
    final int radius = 50;
    final int margin = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.profile_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Collapsing toolbar on change
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Button button = findViewById(R.id.edit_profile_button);
                if (Math.abs(verticalOffset)-appBarLayout.getTotalScrollRange() == 0)
                {
                    //  Collapsed
                    button.setVisibility(View.INVISIBLE);
                }
                else
                {
                    //Expanded
                    button.setVisibility(View.VISIBLE);
                }
            }
        });

        final ImageView userProfilePicture = findViewById(R.id.pp_user_picture);
        if (firebaseUser != null) {
            String id = mAuth.getCurrentUser().getUid();
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
                    TextView userName = findViewById(R.id.pp_user_name);
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
    }
}
