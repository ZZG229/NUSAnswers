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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UploadCheatSheet extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 778;
    TextView textViewForMod;
    Spinner spinnerForAY;
    Spinner spinnerForSem;
    Button buttonForSave;
    Button buttonForBack;
    ImageButton imageButton;
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    Uri filePath;
    String code;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_cheatsheet);

        textViewForMod = findViewById(R.id.textViewForModuleInCheatSheet);
        spinnerForAY = findViewById(R.id.spinnerForAYInCheatSheet);
        spinnerForSem = findViewById(R.id.spinnerForSemInCheatSheet);
        buttonForSave = findViewById(R.id.buttonForSaveQnInCheatSheet);
        buttonForBack = findViewById(R.id.buttonForBackInCheatSheet);
        imageView = findViewById(R.id.imageViewForCheatSheet);
        imageButton = findViewById(R.id.imageButtonInCheatSheet);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("UserContributions");
        intent = getIntent();
        code = intent.getStringExtra("moduleCode");
        textViewForMod.setText(code);
        ArrayAdapter<CharSequence> arrayAdapterForSem = ArrayAdapter.createFromResource(this, R.array.Sem, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForAY = ArrayAdapter.createFromResource(this, R.array.AY, android.R.layout.simple_spinner_item);
        arrayAdapterForSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForSem.setAdapter(arrayAdapterForSem);
        arrayAdapterForAY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForAY.setAdapter(arrayAdapterForAY);

        buttonForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserHome.class));
            }
        });

        buttonForSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
            }
        });
    }

    private void uploadFile() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(code).child("Cheatsheet").child(spinnerForAY.getSelectedItem().toString())
                    .child(spinnerForSem.getSelectedItem().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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

            // Create a record in database
            FirebaseDatabase.getInstance().getReference("UserContribution").child(code)
                    .child("Cheatsheet").child(spinnerForAY.getSelectedItem().toString()).child(spinnerForSem.getSelectedItem().toString())
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("Placeholder");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
