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

import com.example.macintosh.thebakingappproject.Models.Step;

import java.util.ArrayList;

public class StepsDetailFragment extends Fragment {

    private TextView stepInstructionTv;
    private int currentPosition;
    private int nextPosition;
    private int previousPosition;
//    private ArrayList<Step> stepArrayList;
    private int STEP_ARRAY_SIZE;
    private Step step;
    private OnImageClickListener onImageClickListener;


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
        View rootview = inflater.inflate(R.layout.fragment_steps_details,container,false);

        stepInstructionTv = rootview.findViewById(R.id.textView2);


        Bundle bundle = getArguments();
        if(bundle!= null){

//                stepArrayList = bundle.getParcelableArrayList("stepsList");
                STEP_ARRAY_SIZE = bundle.getInt("stepArraySize");
                step = bundle.getParcelable("theNextStep");
                currentPosition = bundle.getInt("currentposition");  // 5
                previousPosition = currentPosition-1;
                nextPosition = currentPosition+1; //6

//                stepInstructionTv.setText(stepArrayList.get(currentPosition).getDescription()); //5
                stepInstructionTv.setText(step.getDescription());
        }


        Button backBtn = showBackButton(rootview);
        Button nextBtn = showNextButton(rootview);

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentPosition< STEP_ARRAY_SIZE-1){
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

//    public void setNextData(int pnextPosition){
//        currentPosition = pnextPosition;
//        previousPosition = currentPosition-1;
//        textView.setText(stepArrayList.get(currentPosition).getShortDescription());
//        if(nextPosition<stepArrayList.size()-1){
//            nextPosition = currentPosition+1;
//        }
//    }
//
//    public void setPreviousData(int previousData) {
//        currentPosition = previousData;
//        nextPosition = currentPosition+1;
//        previousPosition = currentPosition-1;
//        textView.setText(stepArrayList.get(currentPosition).getShortDescription());
//    }

    private void showNextStep(int nextPosition){
        onImageClickListener.onNextPressed(nextPosition); //6, 7
    }

    private void showPreviousStep(int previousPosition){
        onImageClickListener.onBackPressed(previousPosition);
    }

    private Button showNextButton(View view){
        //set the properties for button
        Button btnTag = new Button(getContext());
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
}
