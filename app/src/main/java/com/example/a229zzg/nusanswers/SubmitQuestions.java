package com.example.a229zzg.nusanswers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitQuestions extends AppCompatActivity {
    TextView textViewForMod;
    Spinner spinnerForAY;
    Spinner spinnerForSem;
    Spinner spinnerForType;
    Button buttonForSave;
    Button buttonForBack;
    EditText editTextForQnNum;
    EditText editTextForQnInput;
    FirebaseAuth firebaseAuth;


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
        firebaseAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> arrayAdapterForSem = ArrayAdapter.createFromResource(this,R.array.Sem,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForAY = ArrayAdapter.createFromResource(this,R.array.AY,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arrayAdapterForType = ArrayAdapter.createFromResource(this,R.array.Type,android.R.layout.simple_spinner_item);
        arrayAdapterForSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForSem.setAdapter(arrayAdapterForSem);
        arrayAdapterForAY.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForAY.setAdapter(arrayAdapterForAY);
        arrayAdapterForType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForType.setAdapter(arrayAdapterForType);

        buttonForBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UserHome.class));
            }
        });

        buttonForSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question = new Question(editTextForQnInput.getText().toString(),
                        spinnerForType.getSelectedItem().toString(),spinnerForAY.getSelectedItem().toString(),
                        spinnerForSem.getSelectedItem().toString(),firebaseAuth.getCurrentUser().getUid());
                FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
                firebaseDatabase.getReference().child("User Contribution").child(question.getFilter()).child(question.getYear()).child(question.getSem()).child("Question"+ editTextForQnNum.getText().toString())
                        .child("Content").setValue(question.getContent());
            }
        });




    }
}
