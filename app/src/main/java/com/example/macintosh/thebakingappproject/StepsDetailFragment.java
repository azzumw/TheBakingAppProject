package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

public class StepsDetailFragment extends Fragment {

    private TextView stepInstructionTv;
    private int currentPosition;
    private int nextPosition;
    private int previousPosition;

    private int STEP_ARRAY_SIZE;
    private Step step;

    private OnImageClickListener onImageClickListener;

    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;

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
        simpleExoPlayerView = rootview.findViewById(R.id.simpleExoPlayerView);

        Bundle bundle = getArguments();
        if(bundle!= null){

                STEP_ARRAY_SIZE = bundle.getInt("stepArraySize");
                step = bundle.getParcelable("theNextStep");
                currentPosition = bundle.getInt("currentposition");  // 5
                previousPosition = currentPosition-1;
                nextPosition = currentPosition+1; //6

                ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Step " + step.getId());

                stepInstructionTv.setText(step.getDescription());

                if (step.getVideoURL().length()>0){
                    simpleExoPlayerView.setVisibility(View.VISIBLE);
                    player = MyExoPlayer.getExoPLayerInstance(step.getVideoURL(),getContext());
                    simpleExoPlayerView.setPlayer(player);
                    if(savedInstanceState!=null){
                        long mResumePosition = savedInstanceState.getLong("currentVideoPosition");
                        int  mResumeWindowPostion = savedInstanceState.getInt("currentWindowFrame");
                        player.seekTo(mResumeWindowPostion,mResumePosition);
                        player.setPlayWhenReady(true);
                    }
                }else {
                    simpleExoPlayerView.setVisibility(View.GONE);
                }
        }


        Button backBtn = showBackButton(rootview);
        Button nextBtn = showNextButton(rootview);

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentPosition< STEP_ARRAY_SIZE-1){
                        MyExoPlayer.clearPlayerResources();
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
                        MyExoPlayer.clearPlayerResources();
                        showPreviousStep(previousPosition);
                    }else{
                        Toast.makeText(getContext(),"Start of list",Toast.LENGTH_SHORT).show();
                    }
                }
            });



        return rootview;

    }

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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("currentVideoPosition",player.getCurrentPosition());
        outState.putInt("currentWindowFrame",player.getCurrentWindowIndex());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyExoPlayer.clearPlayerResources();
    }
}
