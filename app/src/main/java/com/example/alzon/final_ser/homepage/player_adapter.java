package com.example.alzon.final_ser.homepage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiowidget.AudioWidget;
import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Belal on 2/23/2017.
 */

public class player_adapter extends RecyclerView.Adapter<player_adapter.ViewHolder> {
    private DatabaseReference mDatabase;
    String piclink;
    String nameLink, nextlink;
    public static int count = 0;
    public static String clicked;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    public static MediaPlayer mediaPlayer;
    private Context context;
    public static List<Upload> uploads;
    public static long startTime = 0;
    public static long finalTime = 0;
    int a;
    SharedPreferences sharedpreferences_fb;


    public player_adapter(Context context, List<Upload> uploads) {
        this.uploads = uploads;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.get().load(uploads.get(position).getUrl()).into(holder.imageView);
        sharedpreferences_fb = context.getApplicationContext().getSharedPreferences("facebook", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences_fb.edit();
        holder.textViewName.setText(uploads.get(position).getName());
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = "playlist";
                Picasso.get().load(uploads.get(position).getUrl()).into(player_activity.img);


                piclink = uploads.get(position).getUrl();
                editor.putString("pic", piclink);
                nameLink = uploads.get(position).getName();
                if (uploads.get(position) == uploads.get(uploads.size() - 1)) {
                    nextlink = uploads.get(position).getAudiourl();

                } else {
                    nextlink = uploads.get(position + 1).getAudiourl();

                }

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(uploads.get(position).getAudiourl()));
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                    mediaPlayer.start();

                    player_adapter.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            player_adapter.mediaPlayer = MediaPlayer.create(context, Uri.parse(player_adapter.uploads.get(position + 1).getAudiourl()));
                            player_adapter.mediaPlayer.start();
                        }
                    });

                } else {

                    player_activity.progress5.setVisibility(View.VISIBLE);

                    mediaPlayer = MediaPlayer.create(v.getContext(), Uri.parse(uploads.get(position).getAudiourl()));
                    mediaPlayer.start();

//                    if(mediaPlayer.isPlaying())
//                    {
//                        player_activity.progress5.setVisibility(View.GONE);
//                    }else {
//                        player_activity.progress5.setVisibility(View.VISIBLE);
//                    }
                    player_adapter.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            player_adapter.mediaPlayer = MediaPlayer.create(context, Uri.parse(player_adapter.uploads.get(position + 1).getAudiourl()));
                            player_adapter.mediaPlayer.start();
                        }
                    });
                    finalTime = mediaPlayer.getDuration();
                    startTime = mediaPlayer.getCurrentPosition();

                }

                editor.putInt("position", position);
                editor.putString("abc", uploads.get(position).albumurl);
                editor.putString("pic", piclink);
                editor.putString("nam", nameLink);
                editor.putString("next", nextlink);
                editor.commit();

                int duration = mediaPlayer.getDuration();
                finalTime = (TimeUnit.MILLISECONDS.toMinutes(duration));
//                        TimeUnit.MILLISECONDS.toSeconds(duration) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))

                int durations = mediaPlayer.getCurrentPosition();
                startTime = (TimeUnit.MILLISECONDS.toMinutes(durations));
//                        TimeUnit.MILLISECONDS.toMinutes(durations),
//                        TimeUnit.MILLISECONDS.toSeconds(durations) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durations)))
//                );
                //     Log.v("times",times.toString());
//                startTime = mediaPlayer.getCurrentPosition();
//                ;
//                finalTime = mediaPlayer.getDuration();
                player_activity.crd.setVisibility(View.VISIBLE);
                player_activity.progress5.setVisibility(View.GONE);
                player_activity.pausee.setVisibility(View.VISIBLE);


            }
        });
//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return uploads.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName, albumname;
        public ImageView imageView;
        public CardView card;
        LinearLayout lin;

        ViewHolder(View itemView) {
            super(itemView);


            textViewName = (TextView) itemView.findViewById(R.id.songname);
            albumname = (TextView) itemView.findViewById(R.id.albumname);
            card = (CardView) itemView.findViewById(R.id.cardv);
            lin = (LinearLayout) itemView.findViewById(R.id.linear);
            imageView = (ImageView) itemView.findViewById(R.id.album);
            //textViewName.setOnClickListener(this);
//            card = (CardView) itemView.findViewById(R.id.cardView3);
//            card.setOnClickListener(this);
//            a=getAdapterPosition();


        }

        public void onDataChange(DataSnapshot snapshot) {
            //dismissing the progress dialog
            progressDialog.dismiss();

            //iterating through all the values in database
            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                Upload upload = postSnapshot.getValue(Upload.class);
                uploads.add(upload);
            }
        }

        @Override
        public void onClick(View v) {

        }


//        @Override
//        public void onClick(View v) {
//
//        }
    }

    public int getLength(String songid) {
        MediaPlayer mp = new MediaPlayer();
        Uri musicURI = Uri.withAppendedPath(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songid);
        try {
            mp.setDataSource(context, musicURI);
            mp.prepare();
        } catch (Exception e) {

        }
        return mp.getDuration();
    }

}