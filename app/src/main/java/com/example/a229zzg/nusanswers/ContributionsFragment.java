package com.example.a229zzg.nusanswers;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * a simple {@link Fragment} subclass.
 */
public class ContributionsFragment extends Fragment {


    public ContributionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contributions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        FrameLayout myLayout = view.findViewById(R.id.contributions_frag_layout);
        TextView message = view.findViewById(R.id.no_contribution_msg);
        myLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.ohno_background));
        message.setVisibility(View.VISIBLE);
    }
}
