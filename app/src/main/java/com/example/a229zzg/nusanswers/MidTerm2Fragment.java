package com.example.a229zzg.nusanswers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
 * a simple {@link Fragment} subclass.
 */
public class MidTerm2Fragment extends Fragment {
    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = mfirebaseDatabase.getReference("UserContribution");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
    List<String> sem = new ArrayList<>();
    String code = null;
    String year = null;

    public MidTerm2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mid_term2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Fetch the module code from arguments
        final Bundle bundle = getArguments();
        code = bundle.getString("moduleCode");
        year = bundle.getString("academicYear");

        FrameLayout myLayout = view.findViewById(R.id.mid_term2_frag_layout);
        final ListView enrolledModules = view.findViewById(R.id.sem_list);
        initialList();
        ModuleList adapter = new ModuleList(getActivity(), sem);
        enrolledModules.setAdapter(adapter);

        enrolledModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String semester = enrolledModules.getItemAtPosition(position).toString();
                MidTerm3Fragment midTerm3Fragment = new MidTerm3Fragment();
                Bundle nextBundle = new Bundle();
                nextBundle.putString("moduleCode", code);
                nextBundle.putString("academicYear", year);
                nextBundle.putString("semester", semester);
                midTerm3Fragment.setArguments(nextBundle);
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.mid_term_frag_layout, midTerm3Fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }
        });

    }

    public boolean initialList() {
        DatabaseReference ref = databaseReference.child(code).child("Mid-term").child(year);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sem.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String semester = dsp.getKey();
                    sem.add(semester);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }
}
