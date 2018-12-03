package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Steps;

import java.util.ArrayList;

public class StepsDetailFragment extends Fragment {

    TextView textView;
    int currentPosition;
    int nextPosition;
    ArrayList<Steps> stepsArrayList;
    OnImageClickListener onImageClickListener;
    public StepsDetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onImageClickListener = (OnImageClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_steps_ingred_details,container,false);

        textView = rootview.findViewById(R.id.textView2);


        Bundle bundle = getArguments();
        if(bundle!= null){

                stepsArrayList = bundle.getParcelableArrayList("stepsList");

                currentPosition = bundle.getInt("currentposition");

                textView.setText(stepsArrayList.get(currentPosition).getShortDescription());

                nextPosition = currentPosition+1;

        }


        Button nextBtn = showNextButton(rootview);


        if(currentPosition<stepsArrayList.size()-currentPosition){
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNextStep(nextPosition);
                }
            });
        }else{
            Toast.makeText(getContext(),"End of List",Toast.LENGTH_SHORT).show();
        }


        return rootview;

    }

    public void setNextData(int pnextPosition){
        currentPosition = pnextPosition;
        textView.setText(stepsArrayList.get(currentPosition).getShortDescription());
        nextPosition = currentPosition+1;

    }

    private Button showNextButton(View view){
        //set the properties for button
        Button btnTag = new Button(getContext());
        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnTag.setText("Button");
        LinearLayout linearLayout= view.findViewById(R.id.linear);
        linearLayout.addView(btnTag);
        return btnTag;

    }


    private void showNextStep(int nextPosition){
//        Steps steps = stepsArrayList.get(nextPosition);
        onImageClickListener.onNextPressed(nextPosition);
    }

    /*
    * if(bundle!=null){
            if(bundle.containsKey("step")){
                Steps step = bundle.getParcelable("currentstep");

                String desc = step.getShortDescription();
                textView.setText(desc);
            }

            else if (bundle.containsKey("nextstep")){
                Steps step = bundle.getParcelable("nextstep");

                String desc = step.getShortDescription();
                textView.setText(desc);
            }


        }*/



}
