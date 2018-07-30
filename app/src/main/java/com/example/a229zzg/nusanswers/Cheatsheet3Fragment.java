package com.example.a229zzg.nusanswers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cheatsheet3Fragment extends Fragment {
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = mfirebaseDatabase.getReference("UserContribution");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
    List<String> userList = new ArrayList<>();
    String code = null;
    String year = null;
    String sem = null;

    public Cheatsheet3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cheatsheet3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Fetch the module code from arguments
        final Bundle bundle = getArguments();
        code = bundle.getString("moduleCode");
        year = bundle.getString("academicYear");
        sem = bundle.getString("semester");

        FrameLayout myLayout = view.findViewById(R.id.cheatsheet3_frag_layout);
        final ListView enrolledModules = view.findViewById(R.id.user_list);
        initialList();
        ModuleList adapter = new ModuleList(getActivity(), userList);
        enrolledModules.setAdapter(adapter);

        enrolledModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String question = enrolledModules.getItemAtPosition(position).toString();
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra("moduleCode", code);
                intent.putExtra("filter", "Mid-term");
                intent.putExtra("academicYear", year);
                intent.putExtra("semester", sem);
                intent.putExtra("question", question);
                startActivity(intent);
            }
        });

    }

    public boolean initialList() {
        DatabaseReference ref = databaseReference.child(code).child("Cheatsheet").child(year).child(sem);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String username = dsp.getKey();
                    userList.add(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }
}
