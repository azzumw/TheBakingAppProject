package com.example.macintosh.thebakingappproject;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MyExoPlayer {
    static SimpleExoPlayer player;

    public static SimpleExoPlayer getExoPLayerInstance(String url, Context context){

        Uri uri = Uri.parse(url);
        String userAgent = Util.getUserAgent(context, context.getApplicationInfo().packageName);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);

        MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(), null, null);

        TrackSelector trackSelector = new DefaultTrackSelector();

        player = ExoPlayerFactory.newSimpleInstance(context,trackSelector);


        player.prepare(mediaSource);

        return player;
    }

    public static void clearPlayerResources(){
        if(player!= null){
            player.stop();
            player.release();
            player = null;
        }
    }
}
