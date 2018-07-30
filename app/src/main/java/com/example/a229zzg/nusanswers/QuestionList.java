package com.example.a229zzg.nusanswers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class QuestionList extends ArrayAdapter<Contribution>{
    private Activity context;
    private List<Contribution> contributionsList;

    public QuestionList(Activity context, List<Contribution> contributionsList) {
        super(context,R.layout.list_for_question,contributionsList);
        this.context = context;
        this.contributionsList = contributionsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewForUerName = listViewItem.findViewById(R.id.UserNameInQnList);
        TextView textViewForAnswer = listViewItem.findViewById(R.id.QuestionInQnList);

        Contribution contribution = contributionsList.get(position);
        textViewForUerName.setText(contribution.getUsername());
        textViewForAnswer.setText(contribution.getAnswer());

        return listViewItem;
    }
}
