package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macintosh.thebakingappproject.Models.Recipe;


public class RecipeDetailMasterListFragment extends Fragment {

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
