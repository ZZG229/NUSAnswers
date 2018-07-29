package com.example.a229zzg.nusanswers;


import android.content.Intent;
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
    List<String> modules = new ArrayList<>();

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
        final ListView enrolledModules = view.findViewById(R.id.user_enrolled_modules);
        ModuleList adapter = new ModuleList(getActivity(), modules);
        enrolledModules.setAdapter(adapter);
        if (!initialList() && modules.isEmpty()) {
            myLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.ohno_background));
            message.setVisibility(View.VISIBLE);
        }

        enrolledModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String moduleFull = enrolledModules.getItemAtPosition(position).toString();
                String module[] = moduleFull.split("\\n");
                Intent intent = new Intent(getActivity(), ModuleHome.class);
                intent.putExtra("moduleCode", module[0]);
                intent.putExtra("moduleName", module[1]);
                startActivity(intent);
            }
        });

    }

    public boolean initialList() {
        DatabaseReference ref = databaseReference.child(firebaseUser.getUid()).child("currentEnrolledModules");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modules.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String module = dsp.getValue(String.class);
                    modules.add(module);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }
}
