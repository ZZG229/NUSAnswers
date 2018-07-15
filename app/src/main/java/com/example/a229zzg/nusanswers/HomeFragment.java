package com.example.a229zzg.nusanswers;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

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
public class HomeFragment extends Fragment {

    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = mfirebaseDatabase.getReference("UserInfo");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
    List<Module> modules = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FrameLayout myLayout = view.findViewById(R.id.home_frag_layout);
        TextView message = view.findViewById(R.id.not_enrolled_msg);
        ListView enrolledModules = view.findViewById(R.id.user_enrolled_modules);
        initialList();
        if (modules.isEmpty()) {
            myLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.ohno_background));
            message.setVisibility(View.VISIBLE);
            return;
        }
        ModuleList adapter = new ModuleList(getActivity(), modules);
        enrolledModules.setAdapter(adapter);

    }

    public void initialList() {
        DatabaseReference ref = databaseReference.child(firebaseUser.getUid()).child("currentEnrolledModules");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modules.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String moduleCode = dsp.getKey();
                    String moduleDescription = dsp.getValue(String.class);
                    Module module = new Module(moduleCode, moduleDescription);
                    modules.add(module);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
