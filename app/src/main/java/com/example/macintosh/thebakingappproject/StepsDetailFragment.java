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
    int previousPosition;
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

                currentPosition = bundle.getInt("currentposition");  // 5
                previousPosition = currentPosition==0? 0:currentPosition-1;
                textView.setText(stepsArrayList.get(currentPosition).getShortDescription()); //5

                nextPosition = currentPosition+1; //6
        }


        Button backBtn = showBackButton(rootview);
        Button nextBtn = showNextButton(rootview);


        //5<7 , 6<7
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentPosition<stepsArrayList.size()-1){
                        showNextStep(nextPosition); //6
                    }
                    else{
                        Toast.makeText(getContext(),"End of List",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentPosition>0){
                        showPreviousStep(previousPosition);
                    }else{
                        Toast.makeText(getContext(),"Start of list",Toast.LENGTH_SHORT).show();
                    }
                }
            });



        return rootview;

    }

    public void setNextData(int pnextPosition){  //6,
        currentPosition = pnextPosition;  //currentposition = 6
        previousPosition = currentPosition-1;
        textView.setText(stepsArrayList.get(currentPosition).getShortDescription());  //6
        if(nextPosition<stepsArrayList.size()-1){
            nextPosition = currentPosition+1;
        }
          //  7

    }

    public void setPreviousData(int previousData) {
        currentPosition = previousData;
        nextPosition = currentPosition+1;
        previousPosition = currentPosition-1;
        textView.setText(stepsArrayList.get(currentPosition).getShortDescription());
    }

    private Button showNextButton(View view){
        //set the properties for button
        Button btnTag = new Button(getContext());
//        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        btnTag.setText("Next");
        btnTag.setLayoutParams(param);
        LinearLayout linearLayout= view.findViewById(R.id.childLinearLayout);
        linearLayout.addView(btnTag);
        return btnTag;

    }
    private Button showBackButton(View view){
        //set the properties for button
        Button btnTag = new Button(getContext());
//        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnTag.setText("Back");

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        btnTag.setLayoutParams(param);
        LinearLayout linearLayout= view.findViewById(R.id.childLinearLayout);
        linearLayout.addView(btnTag);
        return btnTag;

    }


    private void showNextStep(int nextPosition){
//        Steps steps = stepsArrayList.get(nextPosition);
        onImageClickListener.onNextPressed(nextPosition); //6, 7
    }

    private void showPreviousStep(int previousPosition){
        onImageClickListener.onBackPressed(previousPosition);
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
