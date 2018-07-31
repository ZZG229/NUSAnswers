package com.example.a229zzg.nusanswers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ModuleList2 extends BaseAdapter{
    Context context;
    LayoutInflater layoutInflater;
    List<Module> moduleList;
    ArrayList<Module> arrayList;

    public ModuleList2(Context context,List<Module> modules){
        this.context = context;
        this.moduleList = modules;
        this.layoutInflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(modules);
    }

    public class ViewHolder{
        TextView textView;
        TextView textView2;
    }

    @Override
    public int getCount() {
        return moduleList.size();
    }

    @Override
    public Module getItem(int position) {
        return moduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_layout,null,true);
            holder.textView = convertView.findViewById(R.id.Code);
            //holder.textView = convertView.findViewById(R.id.Description);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(moduleList.get(position).getCode());
        holder.textView2.setText(moduleList.get(position).getDescription());
        return convertView;
    }

    public void myFilter(String s){
        s = s.toLowerCase(Locale.getDefault());
        moduleList.clear();
        if (s.length() == 0){
            moduleList.addAll(arrayList);
        }else{
            for (Module module : moduleList){
                if(module.getCode().toLowerCase(Locale.getDefault()).contains(s)){
                    moduleList.add(module);
                }
            }
        }
        notifyDataSetChanged();
    }
}
