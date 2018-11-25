package com.example.macintosh.thebakingappproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class StepIngredDetailFragment extends Fragment {

    public StepIngredDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_steps_ingred_details,container,false);

        ImageView imageView = rootview.findViewById(R.id.imageviewFragStepsIngredDetails);

        imageView.setImageResource(R.drawable.ic_launcher_background);

        return rootview;
    }
}
