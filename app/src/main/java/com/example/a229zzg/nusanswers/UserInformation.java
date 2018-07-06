package com.example.a229zzg.nusanswers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserInformation extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listViewForCompleted;
    ListView listViewForCurrently;
    EditText editTextForCompleted;
    EditText editTextForCurrently;
    List<Module> Modules;
    ArrayAdapter<Module> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ListOfModules");
        listViewForCompleted = findViewById(R.id.listViewForCompleted);
        //listViewForCurrently = findViewById(R.id.listViewForCurrently);
        editTextForCompleted = findViewById(R.id.SearchForCompleted);
        //editTextForCurrently = findViewById(R.id.SearchForCurrently);
        Modules = new ArrayList<>();
        adapter = new ModuleList(UserInformation.this,Modules);


        Spinner spinnerForProgram = findViewById(R.id.SpinnerProgram);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.Programmes,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForProgram.setAdapter(arrayAdapter);
        spinnerForProgram.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),parent.getItemIdAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initialList();
        editTextForCompleted.addTextChangedListener(new TextWatcher() {
            int length;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           length = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    initialList();
                }else {
                    searchItem(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()<length){
                    initialList();
                    for (Module module : Modules){
                        if (!module.getCode().toLowerCase().contains(s.toString().toLowerCase())) {
                            Modules.remove(module);
                        }
                    }
                }
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Modules.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String ModuleCode = dataSnapshot1.getKey();
                    String ModuleDescription = dataSnapshot1.getValue(String.class);
                    Module module = new Module(ModuleCode,ModuleDescription);
                    Modules.add(module);
                }
                adapter = new ModuleList(UserInformation.this,Modules);
                listViewForCompleted.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void searchItem(String s){
        for(Module module:Modules){
            if (!module.getCode().toLowerCase().contains(s.toLowerCase())) {
                Modules.remove(module);
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void initialList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Modules.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String ModuleCode = dataSnapshot1.getKey();
                    String ModuleDescription = dataSnapshot1.getValue(String.class);
                    Module module = new Module(ModuleCode,ModuleDescription);
                    Modules.add(module);
                }
                adapter = new ModuleList(UserInformation.this,Modules);
                listViewForCompleted.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
