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

import com.example.macintosh.thebakingappproject.Models.Steps;

public class StepIngredDetailFragment extends Fragment {

    public StepIngredDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_steps_ingred_details,container,false);

//        ImageView imageView = rootview.findViewById(R.id.imageviewFragStepsIngredDetails);
        TextView textView = rootview.findViewById(R.id.textView2);

        Intent intent = getActivity().getIntent();

        Bundle bundle = intent.getBundleExtra("bundle");

        Steps step = bundle.getParcelable("step");

        String desc = step.getShortDescription();

//        imageView.setImageResource(R.drawable.head1);

        textView.setText(desc);

        return rootview;
    }
}
