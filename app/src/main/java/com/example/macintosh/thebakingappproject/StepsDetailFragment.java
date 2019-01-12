package com.example.macintosh.thebakingappproject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macintosh.thebakingappproject.Models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class StepsDetailFragment extends Fragment {

    private ImageView emtpyImg;
    private TextView stepInstructionTv;
    private int currentPosition;
    private int nextPosition;
    private int previousPosition;

    private Button backBtn;
    private Button nextBtn;

    private int STEP_ARRAY_SIZE;
    private Step step;

    private OnImageClickListener onImageClickListener;

    private SimpleExoPlayer player;
    private PlayerView simpleExoPlayerView;
    private int currentWindow;
    private long playBackPosition;
    private boolean autoPlay = false;

    public static final String AUTOPLAY = "autoplay";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String PLAYBACK_POSITION = "playback_position";

    private boolean mTwoPane = false;

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


    @Override
    public void onResume() {
        super.onResume();
        if(step.getVideoURL().length()>0){

            initialisePlayer();
            if(!mTwoPane){
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    hideSystemUI();
                }
            }


        }else{
            //set the thumbnail in the container
            if(step.getThumbnailURL().length()>0){
                Picasso.with(getContext())
                        .load(step.getThumbnailURL())
                        .placeholder(R.drawable.cupcakeic)
                        .error(R.drawable.cupcakeic)
                        .into(emtpyImg);
            }else {
                emtpyImg.setImageResource(R.drawable.cupcakeic);
            }
        }
    }

    private void initialisePlayer(){

        Uri uri = Uri.parse(step.getVideoURL());

        //create exoplayer object
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);

        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource);
        //attach the player to the view
        simpleExoPlayerView.setPlayer(player);
        player.seekTo(currentWindow,playBackPosition);
        player.setPlayWhenReady(autoPlay);
        simpleExoPlayerView.setVisibility(View.VISIBLE);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DefaultExtractorsFactory extractorSourceFactory = new DefaultExtractorsFactory();
//        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ua");

        String userAgent = Util.getUserAgent(getContext(), getActivity().getApplicationContext().getApplicationInfo().packageName);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent);

        // this return a single mediaSource object. i.e. no next, previous buttons to play next/prev media file
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorSourceFactory, null, null);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("CONFIG","OnConfigurationChange");
        autoPlay = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_steps_details,container,false);
        Log.e("OnCREATEVIEW","ON CREATE VIEW");

        if(rootview.findViewById(R.id.relative_tablet_layout)!= null){
            mTwoPane = true;
        }
        stepInstructionTv = rootview.findViewById(R.id.textView2);
        simpleExoPlayerView = rootview.findViewById(R.id.simpleExoPlayerView);
        emtpyImg = rootview.findViewById(R.id.empty_img_view);
        backBtn = rootview.findViewById(R.id.backbtn);
        nextBtn = rootview.findViewById(R.id.nextbtn);



        Bundle bundle = getArguments();
        if(bundle!= null){

            STEP_ARRAY_SIZE = bundle.getInt(getString(R.string.STEP_ARRAY_SIZE_KEY));
            step = bundle.getParcelable(getString(R.string.NEXT_STEP_KEY));
            currentPosition = bundle.getInt(getString(R.string.CURRENT_POSITON_KEY));
            previousPosition = currentPosition-1;
            nextPosition = currentPosition+1;
        }


        //Tablet
        if(mTwoPane){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Step " + step.getId());
            stepInstructionTv.setVisibility(View.VISIBLE);
            stepInstructionTv.setText(step.getDescription());
            nextButtonListener();
            backButtonListener();
            if(step.getVideoURL().length()==0){
                simpleExoPlayerView.setVisibility(View.GONE);
                emtpyImg.setVisibility(View.VISIBLE);
            }
        }


        //Mobile
        else{
            if(step.getVideoURL().length()>0){
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Step " + step.getId());
                    stepInstructionTv.setText(step.getDescription());
                    nextButtonListener();
                    backButtonListener();
                }

            } else{
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("Step " + step.getId());
                simpleExoPlayerView.setVisibility(View.GONE);
                emtpyImg.setVisibility(View.VISIBLE);
                stepInstructionTv.setVisibility(View.VISIBLE);
                stepInstructionTv.setText(step.getDescription());
//                LinearLayout linearLayout  =  getActivity().findViewById(R.id.childLinearLayout);
//                linearLayout.setVisibility(View.VISIBLE);

            }
            nextButtonListener();
            backButtonListener();
        }

        if(savedInstanceState!= null){
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX);
            playBackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            autoPlay = savedInstanceState.getBoolean(AUTOPLAY);
        }

        return rootview;
    }

    private void nextButtonListener(){
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentPosition< STEP_ARRAY_SIZE-1){
                    releasePlayer();
//                        MyExoPlayer.clearPlayerResources();

                    showNextStep(nextPosition);
                }
                else{
                    Toast.makeText(getContext(),"End of List",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void backButtonListener(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPosition>0){
                    releasePlayer();
//                        MyExoPlayer.clearPlayerResources();
                    showPreviousStep(previousPosition);
                }else{
                    Toast.makeText(getContext(),"Start of list",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void showNextStep(int nextPosition){
        onImageClickListener.onNextPressed(nextPosition);
    }

    private void showPreviousStep(int previousPosition){
        onImageClickListener.onBackPressed(previousPosition);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(player==null){
            outState.putInt(CURRENT_WINDOW_INDEX,currentWindow);
            outState.putLong(PLAYBACK_POSITION,playBackPosition);
            outState.putBoolean(AUTOPLAY,autoPlay);
        }
    }

    private void releasePlayer(){
        if (player != null) {
            currentWindow = player.getCurrentWindowIndex();
            playBackPosition = player.getCurrentPosition();
            autoPlay=player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    private void showSystemUI() {
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getActivity().getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 6){
                    hideSystemUI();
                }else showSystemUI();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(!mTwoPane){

            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle(null);
        }
    }
}
