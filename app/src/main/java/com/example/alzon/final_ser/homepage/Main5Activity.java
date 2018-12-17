package com.example.alzon.final_ser.homepage;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cleveroad.audiowidget.AudioWidget;
import com.example.alzon.final_ser.R;

public class Main5Activity extends AppCompatActivity {
    int position=0;
    AudioWidget audioWidget;
    @Override
    public void onCreate(Bundle savedInstanceState) {
         audioWidget = new AudioWidget.Builder(Main5Activity.this).build();

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static boolean isAppInFg = false;
    public static boolean isScrInFg = false;
    public static boolean isChangeScrFg = false;

    @Override
    protected void onStart() {
        if (!isAppInFg) {
            isAppInFg = true;
            isChangeScrFg = false;
            onAppStart();
        } else {
            isChangeScrFg = true;
        }
        isScrInFg = true;

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isScrInFg || !isChangeScrFg) {
            isAppInFg = false;
            onAppPause();
        }
        isScrInFg = false;
    }


    public void onAppStart() {
        //remove this toast
       // AudioWidget audioWidget = new AudioWidget.Builder(Main5Activity.this).build();
       // audioWidget.show(100, 100);
        audioWidget.hide();
        // your code
    }

    public void onAppPause() {
        //remove this toast
        if(player_adapter.mediaPlayer==null)
        {

        }else {


            if (player_adapter.mediaPlayer.isPlaying()) {
                audioWidget.show(100, 100);
                audioWidget.controller().onControlsClickListener(new AudioWidget.OnControlsClickListener() {

                    @Override
                    public void onPreviousClicked() {
                        if (player_adapter.mediaPlayer != null && player_adapter.mediaPlayer.isPlaying()) {
                            player_adapter.mediaPlayer.stop();
                            player_adapter.mediaPlayer.reset();
                            player_adapter.mediaPlayer.release();
                            player_adapter.mediaPlayer = null;
                        }

                        //  tx.setText(player_adapter.uploads.get(position - 1).getName());
                        player_adapter.mediaPlayer = MediaPlayer.create(Main5Activity.this, Uri.parse(player_adapter.uploads.get(position - 1).getAudiourl()));
                        player_adapter.mediaPlayer.start();
                    }

                    @Override
                    public boolean onPlayPauseClicked() {
                        if (player_adapter.mediaPlayer.isPlaying()) {
                            player_adapter.mediaPlayer.pause();
                        } else {
                            player_adapter.mediaPlayer.start();
                        }
                        return true;
                    }

                    @Override
                    public void onNextClicked() {
                        Intent in = getIntent();
                        Bundle bn = in.getExtras();
                        if (bn != null) {

                            position = bn.getInt("position", 0);
                        }
                        if (player_adapter.mediaPlayer != null && player_adapter.mediaPlayer.isPlaying()) {
                            player_adapter.mediaPlayer.stop();
                            player_adapter.mediaPlayer.reset();
                            player_adapter.mediaPlayer.release();
                            player_adapter.mediaPlayer = null;
                        }

                        //  tx.setText(player_adapter.uploads.get(position - 1).getName());
                        player_adapter.mediaPlayer = MediaPlayer.create(Main5Activity.this, Uri.parse(player_adapter.uploads.get(position + 1).getAudiourl()));
                        player_adapter.mediaPlayer.start();
                    }

                    @Override
                    public void onAlbumClicked() {
                        Intent in = new Intent(Main5Activity.this, player_activity.class);
                        startActivity(in);
                    }

                    @Override
                    public boolean onPlaylistClicked() {
                        Toast.makeText(Main5Activity.this, "This is my Toast message!",
                                Toast.LENGTH_LONG).show();
                        return false;
                    }

                    @Override
                    public void onPlaylistLongClicked() {
                        // playlist button long clicked
                    }

                    @Override
                    public void onPreviousLongClicked() {
                        // previous track button long clicked
                    }

                    @Override
                    public void onPlayPauseLongClicked() {
                        // play/pause button long clicked
                    }

                    @Override
                    public void onNextLongClicked() {
                        // next track button long clicked
                    }


                    @Override
                    public void onAlbumLongClicked() {
                        // album cover long clicked
                    }
                });
                Log.e("TAG", "Activity Minimized");
            }
        }

        // your code
    }
}
