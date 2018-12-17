package com.example.alzon.final_ser.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.database.DatabaseHelper;
import com.example.alzon.final_ser.favorite.favAdapter;
import com.example.alzon.final_ser.favorite.favorite_list;
import com.example.alzon.final_ser.homepage.Main5Activity;
import com.example.alzon.final_ser.homepage.malayalam_adapter;
import com.example.alzon.final_ser.homepage.player_adapter;
import com.example.alzon.final_ser.homepage.tamil_adapter;
import com.example.alzon.final_ser.modelclass.Upload;
import com.example.alzon.final_ser.modelclass.favorite_modelclass;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.alzon.final_ser.homepage.player_adapter.startTime;

public class Main4Activity extends Main5Activity {
    SeekBar seek_bar;
    Button play_button, pause_button;
    MediaPlayer player;
    TextView text_shown;
    Handler seekHandler = new Handler();
    ImageView imageView, pause, next, prev;
    // CircleImageView img;
    SeekBar seek;
    private static final float BLUR_RADIUS = 25f;
    public static Bitmap bt;


    public static int oneTimeOnly = 0;
    //    @BindView(R.id.profile_image)
//    CircleImageView profileImage;
    @BindView(R.id.seekBar2)
    SeekBar seekBar2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.textView3)
    TextView textView3;
    //    @BindView(R.id.textView)
//    TextView textView;
    @BindView(R.id.imageView)
    ImageView imageViews;
    @BindView(R.id.favorite)
    ImageView favorite;
    int length;
    CardView cardView9;


    private Handler myHandler = new Handler();
    ;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    int position;
    TextView tx, tx2, tx3;
    SharedPreferences sp;
    String picurl, nextlink;
    Toolbar mTopToolbar;
    ImageView pausebtn;
    ImageView bac;
    List<Upload> playerlist;
    List<Upload> favourites;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        ButterKnife.bind(this);
        playerlist = new ArrayList<>();
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        pausebtn = findViewById(R.id.play);
        prev = findViewById(R.id.prev);
        imageView = findViewById(R.id.img);
        bac = findViewById(R.id.bac);
        // img = findViewById(R.id.profile_image);
        tx = findViewById(R.id.textView);
        seek = findViewById(R.id.seekBar2);
        Intent in = getIntent();
        Bundle bn = in.getExtras();

        if (bn != null) {
            position = bn.getInt("position", 0);

        }
//        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
//        mTopToolbar.setTitleTextColor(Color.BLACK);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), player_activity.class));
//                finish();
//            }
//        });
        Log.v("sdfsdfg", String.valueOf(startTime));
        imageViews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViews.setVisibility(View.GONE);
                favorite.setVisibility(View.VISIBLE);

            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favourites = new ArrayList<>();
                int positions;
                sp = Main4Activity.this.getApplicationContext().getSharedPreferences("facebook", Context.MODE_PRIVATE);
                positions = sp.getInt("position", 0);

                favorite.setVisibility(View.GONE);
                imageViews.setVisibility(View.VISIBLE);
                String abc = player_adapter.uploads.get(positions).getAudiourl() + "\n" + player_adapter.uploads.get(positions).getName();
                String a = abc;
                favorite_modelclass upload = new favorite_modelclass();
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                upload.setAlbumurl(player_adapter.uploads.get(positions).getUrl());
                upload.setAudiourl(player_adapter.uploads.get(positions).getAudiourl());
                upload.setName(player_adapter.uploads.get(positions).getName());
                db.favoriteinsert(upload);
            }
        });
        tx2 = findViewById(R.id.textView2);
        tx3 = findViewById(R.id.textView3);

        sp = Main4Activity.this.getApplicationContext().getSharedPreferences("facebook", Context.MODE_PRIVATE);
        picurl = sp.getString("pic", null);
        nextlink = sp.getString("next", null);
        getInit();
        seekUpdation();


        Picasso.get().load(picurl).into(bac);
//
        if (favorite_list.clicked == "favorite") {
            Intent ins = getIntent();
            Bundle bns = ins.getExtras();
            int positions = sp.getInt("positionss", 0);
            int pod=positions;
//            if (bns != null) {
//                position = bns.getInt("position", 0);
//
//            }
            seek.setProgress((int) (startTime));
            //   myHandler.postDelayed(UpdateSongTime, 100);
            //tx.setText(player_adapter.uploads.get(positions).getName());
            pause = findViewById(R.id.pause);
            next = findViewById(R.id.next);
            prev = findViewById(R.id.prev);

            pause.setBackgroundResource(R.drawable.play_btn);
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        rotateAnimation.setDuration(2000);
//        rotateAnimation.setRepeatCount(Animation.INFINITE);
//
//        findViewById(R.id.profile_image).startAnimation(rotateAnimation);
            //  new GetImageFromURL(imageView).execute();
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (player_adapter.mediaPlayer.isPlaying()) {
                        player_adapter.mediaPlayer.stop();
                    }
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favAdapter.mediaPlayer != null && favAdapter.mediaPlayer.isPlaying()) {
                        favAdapter.mediaPlayer.stop();
                        favAdapter.mediaPlayer.reset();
                        favAdapter.mediaPlayer.release();
                        favAdapter.mediaPlayer = null;
                    }

                    Log.v("abcd", nextlink);
                    player_adapter.mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(nextlink));
                    player_adapter.mediaPlayer.start();
                    tx.setText(favAdapter.favorite.get(position + 1).getName());
                }
            });
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favAdapter.mediaPlayer != null && favAdapter.mediaPlayer.isPlaying()) {
                        favAdapter.mediaPlayer.stop();
                        favAdapter.mediaPlayer.reset();
                        favAdapter.mediaPlayer.release();
                        favAdapter.mediaPlayer = null;
                    }

                    tx.setText(player_adapter.uploads.get(position - 1).getName());
                    player_adapter.mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(favAdapter.favorite.get(position - 1).getAudiourl()));
                    player_adapter.mediaPlayer.start();
                }
            });

            Intent ink = getIntent();
            Bundle bnk = ins.getExtras();
            if (bn != null) {
                position = bns.getInt("position", 0);

            }

            Picasso.get().load(picurl).into(bac);


        } else if (player_adapter.clicked == "playlist") {
            seek.setProgress((int) (startTime));
            //   myHandler.postDelayed(UpdateSongTime, 100);
            tx.setText(player_adapter.uploads.get(position).getName());
            pause = findViewById(R.id.pause);
            next = findViewById(R.id.next);
            prev = findViewById(R.id.prev);

            pause.setBackgroundResource(R.drawable.play_btn);
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        rotateAnimation.setDuration(2000);
//        rotateAnimation.setRepeatCount(Animation.INFINITE);
//
//        findViewById(R.id.profile_image).startAnimation(rotateAnimation);
            //  new GetImageFromURL(imageView).execute();
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (player_adapter.mediaPlayer.isPlaying()) {
                        player_adapter.mediaPlayer.stop();
                    }
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (player_adapter.mediaPlayer != null && player_adapter.mediaPlayer.isPlaying()) {
                        player_adapter.mediaPlayer.stop();
                        player_adapter.mediaPlayer.reset();
                        player_adapter.mediaPlayer.release();
                        player_adapter.mediaPlayer = null;
                    }

                    Log.v("abcd", nextlink);
                    player_adapter.mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(nextlink));
                    player_adapter.mediaPlayer.start();
                    tx.setText(player_adapter.uploads.get(position + 1).getName());
                }
            });
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (player_adapter.mediaPlayer != null && player_adapter.mediaPlayer.isPlaying()) {
                        player_adapter.mediaPlayer.stop();
                        player_adapter.mediaPlayer.reset();
                        player_adapter.mediaPlayer.release();
                        player_adapter.mediaPlayer = null;
                    }

                    tx.setText(player_adapter.uploads.get(position - 1).getName());
                    player_adapter.mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(player_adapter.uploads.get(position - 1).getAudiourl()));
                    player_adapter.mediaPlayer.start();
                }
            });

            Intent inc = getIntent();
            Bundle bnc = in.getExtras();
            if (bn != null) {
                position = bn.getInt("position", 0);

            }

            Picasso.get().load(picurl).into(bac);

        }


    }


    public void getInit() {
        seek_bar = (SeekBar) findViewById(R.id.seekBar2);

        player = player_adapter.mediaPlayer;
//        seek_bar.setMax(player.getDuration());

    }

    Runnable run = new Runnable() {

        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        player = player_adapter.mediaPlayer;
        // seek_bar.setProgress(player.getCurrentPosition());
        // seekHandler.postDelayed(run, 1000);
    }


}

