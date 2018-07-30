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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private Intent intent;
    private String code;
    private String filter;
    private String year;
    private String sem;
    private String question;
    private int answerNum;
    private ArrayList<Contribution> arrayList;
    private QuestionList questionList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("UserInfo");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        final int radius = 50;
        final int margin = 5;

        // Intent
        intent = getIntent();
        code = intent.getStringExtra("moduleCode");
        filter = intent.getStringExtra("filter");
        year = intent.getStringExtra("academicYear");
        sem = intent.getStringExtra("semester");
        question = intent.getStringExtra("question");
        DatabaseReference contentRef = FirebaseDatabase.getInstance().getReference("UserContributions").child(code).child(filter)
                .child(year).child(sem).child(question);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Loading related information onto App bar
        final ImageView userProfilePicture = findViewById(R.id.user_profile_pic);
        if (firebaseUser != null) {
            contentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild("Uid")) {
                        String id = (String) dataSnapshot.child("Uid").getValue();
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
                                TextView userName = findViewById(R.id.user_name);
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

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        TextView questionTitle = findViewById(R.id.question_title);
        TextView innerQuestionTitle = findViewById(R.id.inner_question_title);
        questionTitle.setText(question);
        innerQuestionTitle.setText(question);

        // Load user uploaded image of question if available
        final ImageView userUpload = findViewById(R.id.user_upload);
        FirebaseStorage.getInstance().getReference("UserContributions").child(code).child(filter)
                .child(year).child(sem).child(question).child("Content").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().
                                transform(new RoundTransformation(radius,margin)).into(userUpload);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        userUpload.setVisibility(View.GONE);
                    }
                });

        // Load user uploaded description of question if available
        contentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TextView content = findViewById(R.id.question_content);
                if(dataSnapshot.hasChild("Content")) {
                    String questionContent = (String) dataSnapshot.child("Content").getValue();
                    content.setText(questionContent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                TextView content = findViewById(R.id.question_content);
                content.setVisibility(View.GONE);
            }
        });

        arrayList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserContributions").child(code).child(filter)
                .child(year).child(sem).child(question).child("Answer").child("answerNum");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.hasChildren()){
                        final String answer = (String) dataSnapshot1.child("Content").getValue();
                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("UserInfo")
                                .child(dataSnapshot1.child("Uid").getValue().toString()).child("username");
                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String username = (String) dataSnapshot.getValue();
                                Contribution contribution = new Contribution(username,answer);
                                arrayList.add(contribution);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        questionList = new QuestionList(this,arrayList);
        listView = findViewById(R.id.ListViewForContribution);
        listView.setAdapter(questionList);

        FloatingActionButton fab = findViewById(R.id.submitAnswerButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // temp
                answerNum = 0;

                Intent submitAnswer = new Intent(QuestionActivity.this, SubmitAnswer.class);
                submitAnswer.putExtra("moduleCode", code);
                submitAnswer.putExtra("filter", filter);
                submitAnswer.putExtra("academicYear", year);
                submitAnswer.putExtra("semester", sem);
                submitAnswer.putExtra("question", question);
                submitAnswer.putExtra("answerNumber", answerNum);
                startActivity(submitAnswer);
            }
        });


    }
}
