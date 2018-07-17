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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserInformation extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseAuth firebaseAuth;
    ListView listViewForCompleted;
    ListView listViewForCurrently;
    EditText editTextForCompleted;
    EditText editTextForCurrently;
    ArrayList<Module> modules = new ArrayList<>();
    ModuleList adapter;
    //ArrayAdapter<Module> adapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    /*
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ListOfModules");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserInfo");

        listViewForCompleted = findViewById(R.id.listViewForCompleted);
        //listViewForCurrently = findViewById(R.id.listViewForCurrently);
        editTextForCompleted = findViewById(R.id.SearchForCompleted);
        //editTextForCurrently = findViewById(R.id.SearchForCurrently);
        button.findViewById(R.id.buttonToSaveProgram);
        firebaseAuth = FirebaseAuth.getInstance();


        final Spinner spinnerForProgram = findViewById(R.id.SpinnerProgram);
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Programmes, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForProgram.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot1.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                                UserInfo userInfo = dataSnapshot1.getValue(UserInfo.class);
                                userInfo.setProgram(spinnerForProgram.getSelectedItem().toString());
                                databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).removeValue();
                                databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).setValue(userInfo);
                                Toast.makeText(getApplicationContext(), spinnerForProgram.getSelectedItem().toString() + " has been saved", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected",Toast.LENGTH_LONG).show();

        initialList();
        adapter = new ModuleList2(this,modules);
        listViewForCompleted.setAdapter(adapter);
        editTextForCompleted.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String module = editTextForCompleted.getText().toString().toLowerCase(Locale.getDefault());
                adapter.myFilter(module);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listViewForCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Module module = modules.get(position);
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserInfo userInfo = null;
                        ArrayList<Module> arrayList;
                        for(DataSnapshot dsp:dataSnapshot.getChildren()) {
                            if (firebaseAuth.getCurrentUser().getUid().equals(dsp.getKey())) {
                                userInfo = dsp.getValue(UserInfo.class);
                                if (userInfo.getCompletedModules() != null) {
                                    arrayList = userInfo.getCompletedModules();
                                } else {
                                    arrayList = new ArrayList<>();
                                }
                                arrayList.add(module);
                                userInfo.setCompletedModules(arrayList);
                                break;
                            }
                        }
                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).removeValue();
                        databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).setValue(userInfo);
                        Toast.makeText(getApplicationContext(), module.getCode() + " has been saved", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    public void initialList() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modules.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String moduleCode = dataSnapshot1.getKey();
                    String moduleDescription = dataSnapshot1.getValue(String.class);
                    Module module = new Module(moduleCode, moduleDescription);
                    modules.add(module);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ListOfModules");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserInfo");

        listViewForCompleted = findViewById(R.id.listViewForCompleted);
        //listViewForCurrently = findViewById(R.id.listViewForCurrently);
        editTextForCompleted = findViewById(R.id.SearchForCompleted);
        //editTextForCurrently = findViewById(R.id.SearchForCurrently);
        modules = new ArrayList<>();
        adapter = new ModuleList(UserInformation.this, modules);
        firebaseAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.buttonToSaveProgram);

        final Spinner spinnerForProgram = findViewById(R.id.SpinnerProgram);
        final ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Programmes, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForProgram.setAdapter(arrayAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot1.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                                UserInfo userInfo = dataSnapshot1.getValue(UserInfo.class);
                                userInfo.setProgram(spinnerForProgram.getSelectedItem().toString());
                                databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).removeValue();
                                databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).setValue(userInfo);
                                Toast.makeText(getApplicationContext(),spinnerForProgram.getSelectedItem().toString()+ " has been saved",Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+" selected",Toast.LENGTH_LONG).show();
            }

        });


        initialList();
        editTextForCompleted.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    initialList();
                } else {
                    searchItem(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        listViewForCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Module module = modules.get(position);
                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserInfo userInfo = null;
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            if (dsp.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                                userInfo = dsp.getValue(UserInfo.class);
                                ArrayList<Module> arrayList;
                                if (userInfo.getCompletedModules() != null) {
                                    arrayList = userInfo.getCompletedModules();
                                } else {
                                    arrayList = new ArrayList<>();
                                }
                                arrayList.add(module);
                                userInfo.setCompletedModules(arrayList);
                                break;
                            }
                        }
                        if (userInfo != null) {
                            databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).removeValue();
                            databaseReference2.child(firebaseAuth.getCurrentUser().getUid()).setValue(userInfo);
                            Toast.makeText(getApplicationContext(), module.getCode() + " has been saved", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modules.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String ModuleCode = dataSnapshot1.getKey();
                    String ModuleDescription = dataSnapshot1.getValue(String.class);
                    Module module = new Module(ModuleCode,ModuleDescription);
                    modules.add(module);
                }
                adapter = new ModuleList(UserInformation.this, modules);
                listViewForCompleted.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
*/
    public void searchItem(String s){
        for (Module module: modules) {
            if (!module.getCode().toLowerCase().contains(s.toLowerCase())) {
                modules.remove(module);
            } else if (module.getCode().toLowerCase().contains(s) && !modules.contains(module)) {
                modules.add(module);
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void initialList(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modules.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String moduleCode = dataSnapshot1.getKey();
                    String moduleDescription = dataSnapshot1.getValue(String.class);
                    Module module = new Module(moduleCode, moduleDescription);
                    modules.add(module);
                }
                adapter = new ModuleList(UserInformation.this, modules);
                listViewForCompleted.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
