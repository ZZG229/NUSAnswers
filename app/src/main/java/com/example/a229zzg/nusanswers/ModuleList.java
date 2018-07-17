package com.example.a229zzg.nusanswers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ModuleList extends ArrayAdapter<String> {
    private Activity context;
    private List<String> moduleList;

    public ModuleList(Activity context, List<String> moduleList) {
        super(context,R.layout.list_layout,moduleList);
        this.context = context;
        this.moduleList = moduleList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView textViewCode = listViewItem.findViewById(R.id.Code);

        String module = moduleList.get(position);
        textViewCode.setText(module);

        return listViewItem;
    }
}
