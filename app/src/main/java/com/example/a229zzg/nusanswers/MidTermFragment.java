package com.example.a229zzg.nusanswers;

import android.content.Context;
import android.content.Intent;
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
public class MidTermFragment extends Fragment {

    // Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = mfirebaseDatabase.getReference("UserContribution");
    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
    List<String> year = new ArrayList<>();
    String code = null;

    public MidTermFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mid_term, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Fetch the module code from arguments
        final Bundle bundle = getArguments();
        code = bundle.getString("moduleCode");

        FrameLayout myLayout = view.findViewById(R.id.mid_term_frag_layout);
        TextView message = view.findViewById(R.id.no_contribution_msg);
        final ListView enrolledModules = view.findViewById(R.id.academic_year_list);
        if (!initialList() && year.isEmpty()) {
            myLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.ohno_background));
            message.setVisibility(View.VISIBLE);
        }
        ModuleList adapter = new ModuleList(getActivity(), year);
        enrolledModules.setAdapter(adapter);

        enrolledModules.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String academicYear = enrolledModules.getItemAtPosition(position).toString();
                MidTerm2Fragment midTerm2Fragment = new MidTerm2Fragment();
                Bundle nextBundle = new Bundle();
                nextBundle.putString("moduleCode", code);
                nextBundle.putString("academicYear", academicYear);
                midTerm2Fragment.setArguments(nextBundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, midTerm2Fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
                if (midTerm2Fragment.isAdded()) {
                    return;
                }
            }
        });

    }

    public boolean initialList() {
        if (code == null) {
            return false;
        }

        DatabaseReference ref = databaseReference.child(code).child("Mid-term");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                year.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String aYear = dsp.getKey();
                    year.add(aYear);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }
}
