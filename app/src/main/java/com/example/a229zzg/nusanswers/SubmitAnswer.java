package com.example.a229zzg.nusanswers;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SubmitAnswer extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 777;
    private Button buttonForBack;
    private Button buttonForSave;
    private ImageButton imageButton;
    private EditText editText;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private Uri filePath;
    private Intent intent;
    private String code;
    private String filter;
    private String year;
    private String sem;
    private String question;
    private int answerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_answer);
        buttonForBack = findViewById(R.id.buttonForBackInSubmitAns);
        buttonForSave = findViewById(R.id.buttonForSaveSubmitAns);
    //    imageButton = findViewById(R.id.imageButtonForSubAns);
        editText = findViewById(R.id.editTextForSubmitAns);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("UserContributions");

        intent = getIntent();
        code = intent.getStringExtra("moduleCode");
        filter = intent.getStringExtra("filter");
        year = intent.getStringExtra("academicYear");
        sem = intent.getStringExtra("semester");
        question = intent.getStringExtra("question");
        answerNum = intent.getIntExtra("answerNumber", -1);

        buttonForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonForSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer answer = new Answer(editText.getText().toString(),firebaseAuth.getCurrentUser().getUid());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("UserContribution").child(code).child(filter).child(year).child(sem).child(question).child("Answer")
                        .child("answerNum").child("Content").setValue(answer.getContent());
                firebaseDatabase.getReference().child("UserContribution").child(code).child(filter).child(year).child(sem).child(question).child("Answer")
                        .child("answerNum").child("Uid").setValue(firebaseAuth.getUid());
        //        uploadFile();
                finish();
            }
        });
/*
        imageButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
            }
        }); */
    }
    /*
    private void uploadFile() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(code).child(filter).child(year).child(sem).child(question).child("Answer").child("answerNum");
            storageReference2.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Picture uploaded",Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred())/ taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage((int) progress+ "% uploading...");
                }
            });
        }
    }
*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
