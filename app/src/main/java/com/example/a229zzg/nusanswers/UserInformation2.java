package com.example.a229zzg.nusanswers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInformation2 extends AppCompatActivity {
    Button button;
    ListView listViewForCompleted;
    EditText editText;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ArrayList<String> modules;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information2);
        button = findViewById(R.id.buttonToSaveCompleted);
        listViewForCompleted = findViewById(R.id.listViewForCompleted);
        editText = findViewById(R.id.SearchForCompleted);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ListOfModules");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserInfo");

        modules = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserInformation2.this.modules.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String moduleCode = dataSnapshot1.getKey();
                    String moduleDescription = dataSnapshot1.getValue(String.class);
                    String moduleAll = moduleCode + "\n" + moduleDescription;
                    UserInformation2.this.modules.add(moduleAll);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        adapter = new ArrayAdapter<>(this,R.layout.list_layout,R.id.Code,modules);
        listViewForCompleted.setTextFilterEnabled(true);
        listViewForCompleted.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                UserInformation2.this.adapter.getFilter().filter(s);
                UserInformation2.this.adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        listViewForCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String module = listViewForCompleted.getItemAtPosition(position).toString();
                //final String module = modules.get(position);

                databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserInfo userInfo = null;
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            if (dsp.getKey().equals(firebaseAuth.getCurrentUser().getUid())) {
                                userInfo = dsp.getValue(UserInfo.class);
                                ArrayList<String> arrayList;
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
                            Toast.makeText(getApplicationContext(), module + " has been saved", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserHome.class);
                startActivity(intent);
            }
        });



    }
}
