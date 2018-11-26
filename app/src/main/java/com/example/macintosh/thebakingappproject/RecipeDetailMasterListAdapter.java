package com.example.macintosh.thebakingappproject;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.Inflater;


public class RecipeDetailMasterListAdapter extends BaseAdapter{
    private List<String> stringList;
    private Context context;

    public RecipeDetailMasterListAdapter(int size, Context context) {
        this.context = context;
        stringList = new ArrayList<>();
        size++;
        fillArrayList(size);
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.main_recipe_list_item,parent,false);
        }

        String currentString = (String) getItem(position);

        TextView textView =  convertView.findViewById(R.id.recipeTitleTv);
        textView.setText(currentString);


        return convertView;
    }

    private void fillArrayList(int size){
        stringList.add("Ingredients");
        for(int i=1; i < size; i++){
            stringList.add("Step " + i);
        }
    }
}