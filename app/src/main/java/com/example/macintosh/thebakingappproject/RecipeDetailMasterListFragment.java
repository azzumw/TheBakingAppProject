package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.macintosh.thebakingappproject.Models.Recipe;

public class RecipeDetailMasterListFragment extends Fragment {

    private Recipe recipe;
    private LinearLayoutManager linearLayoutManager;


    OnImageClickListener mCallBack;



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

//        Intent intent = getActivity().getIntent();
        Bundle bundle = getArguments();
        recipe = bundle.getParcelable("bundle");
//        recipe = intent.getParcelableExtra("recipe");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(recipe.getName());
        Log.e(getClass().getSimpleName(),recipe.getName());


//        RecyclerView recyclerView = rootView.findViewById(R.id.masterListRecyclerView);
        ListView listView = rootView.findViewById(R.id.masterListView);

//        linearLayoutManager = new LinearLayoutManager(getContext());

//        recyclerView.setLayoutManager(linearLayoutManager);
        //adapter
        RecipeDetailMasterListAdapter adapter = new RecipeDetailMasterListAdapter(recipe.getSteps().size(),getContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.onItemClicked(position,recipe);           //position: 1
            }
        });
        return rootView;
    }





}
