package com.example.a229zzg.nusanswers;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * a simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ListView listViewForCurrently;
    EditText editTextForCurrently;
    ArrayList<String> modules;
    ArrayAdapter<String> adapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ListOfModules");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("UserInfo");

        listViewForCurrently = view.findViewById(R.id.listViewForCurrent);
        editTextForCurrently = view.findViewById(R.id.SearchForCurrent);

        modules = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modules.clear();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String moduleCode = dataSnapshot1.getKey();
                    String moduleDescription = dataSnapshot1.getValue(String.class);
                    String moduleAll = moduleCode + "\n" + moduleDescription;
                    modules.add(moduleAll);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ArrayAdapter<>(getActivity(),R.layout.list_layout,R.id.Code,modules);
        listViewForCurrently.setTextFilterEnabled(true);
        listViewForCurrently.setAdapter(adapter);

        editTextForCurrently.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        listViewForCurrently.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String moduleFull = listViewForCurrently.getItemAtPosition(position).toString();
                String module[] = moduleFull.split("\\n");
                Intent intent = new Intent(getActivity(), ModuleHome.class);
                intent.putExtra("moduleCode", module[0]);
                intent.putExtra("moduleName", module[1]);
                startActivity(intent);
            }
        });
    }
}
