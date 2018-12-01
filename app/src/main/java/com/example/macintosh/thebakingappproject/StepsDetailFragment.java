package com.example.macintosh.thebakingappproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macintosh.thebakingappproject.Models.Ingredient;
import com.example.macintosh.thebakingappproject.Models.Steps;

import java.util.ArrayList;

public class StepsDetailFragment extends Fragment {

    public StepsDetailFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_steps_ingred_details,container,false);

        TextView textView = rootview.findViewById(R.id.textView2);



        Bundle bundle = getArguments();
        if(bundle!=null){


                Steps step = bundle.getParcelable("step");

                String desc = step.getShortDescription();
                textView.setText(desc);

        }

        return rootview;

    }


}
