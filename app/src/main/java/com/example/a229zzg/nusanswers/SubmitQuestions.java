package com.example.a229zzg.nusanswers;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class SubmitQuestions extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 234;
    TextView textViewForMod;
    Spinner spinnerForAY;
    Spinner spinnerForSem;
    Spinner spinnerForType;
    Button buttonForSave;
    Button buttonForBack;
    ImageButton imageButton;
    EditText editTextForQnNum;
    EditText editTextForQnInput;
    FirebaseAuth firebaseAuth;
    StorageReference storageReference;
    Uri filePath;
    String code;
    Intent intent;

    @Override
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_questions);
        textViewForMod = findViewById(R.id.textViewForModule);
        spinnerForAY = findViewById(R.id.spinnerForAY);
        spinnerForSem = findViewById(R.id.spinnerForSem);
        spinnerForType = findViewById(R.id.spinnerForType);
        buttonForSave = findViewById(R.id.buttonForSaveQn);
        buttonForBack = findViewById(R.id.buttonForBack);
        editTextForQnNum = findViewById(R.id.editTextForEnterQnNum);
        editTextForQnInput = findViewById(R.id.editTextForQuestionDescription);
        imageButton = findViewById(R.id.imageButtonForUploadQnPic);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("UserContributions");
        intent = getIntent();
        code = intent.getStringExtra("moduleCode");
        textViewForMod.setText(code);

        ArrayAdapter<CharSequence> arrayAdapterForSem = ArrayAdapter.createFromResource(this, R.array.Sem, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForAY = ArrayAdapter.createFromResource(this, R.array.AY, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForType = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        arrayAdapterForSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForSem.setAdapter(arrayAdapterForSem);
        arrayAdapterForAY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForAY.setAdapter(arrayAdapterForAY);
        arrayAdapterForType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForType.setAdapter(arrayAdapterForType);

        buttonForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonForSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question = new Question(editTextForQnInput.getText().toString(),
                        spinnerForType.getSelectedItem().toString(), spinnerForAY.getSelectedItem().toString(),
                        spinnerForSem.getSelectedItem().toString(), firebaseAuth.getCurrentUser().getUid());
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("UserContribution").child(code).child(question.getFilter()).child(question.getYear()).child(question.getSem()).child("Question" + editTextForQnNum.getText().toString())
                        .child("Content").setValue(question.getContent());
                firebaseDatabase.getReference().child("UserContribution").child(code).child(question.getFilter()).child(question.getYear()).child(question.getSem()).child("Question" + editTextForQnNum.getText().toString())
                        .child("Uid").setValue(question.getUid());
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
            Question question = new Question(editTextForQnInput.getText().toString(),
                    spinnerForType.getSelectedItem().toString(), spinnerForAY.getSelectedItem().toString(),
                    spinnerForSem.getSelectedItem().toString(), firebaseAuth.getCurrentUser().getUid());
            StorageReference storageReference2 = storageReference.child(code).child(question.getFilter()).child(question.getYear()).child(question.getSem()).child("Question" + editTextForQnNum.getText().toString())
                    .child("Content");
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

}
