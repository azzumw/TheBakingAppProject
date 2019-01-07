package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.macintosh.thebakingappproject.Models.Recipe;

public class RecipeDetailMasterListFragment extends Fragment{

    private Recipe recipe;

    private OnImageClickListener mCallBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnImageClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }

    public RecipeDetailMasterListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list,container,false);

        Bundle bundle = getActivity().getIntent().getParcelableExtra("rBundle");

        recipe = bundle.getParcelable("bundle");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(recipe.getName());

        RecyclerView recyclerView = rootView.findViewById(R.id.masterListRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        //adapter
        RecipeDetailMasterListRecyclerAdapter recyclerAdapter = new RecipeDetailMasterListRecyclerAdapter(getContext(),recipe.getSteps().size(),mCallBack);

        recyclerView.setAdapter(recyclerAdapter);

        return rootView;
    }
}
