package com.example.alzon.final_ser.favorite;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alzon.final_ser.MainActivity;
import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.demo.Main4Activity;
import com.example.alzon.final_ser.homepage.player;
import com.example.alzon.final_ser.homepage.player_activity;
import com.example.alzon.final_ser.homepage.player_adapter;
import com.example.alzon.final_ser.homepage.tamil_adapter;
import com.example.alzon.final_ser.modelclass.Upload;
import com.example.alzon.final_ser.modelclass.favorite_modelclass;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class favAdapter extends RecyclerView.Adapter<favAdapter.ViewHolder> {
    ////values that need to be passed///
    public static List<favorite_modelclass> favorite;
    SharedPreferences sharedpreferences_fb;
    Context context;
   public static MediaPlayer mediaPlayer;
    public favAdapter(Context context, List<favorite_modelclass> favorite) {

        this.context = context;
        this.favorite = favorite;
    }


    @Override
    public favAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite, parent, false);
        favAdapter.ViewHolder viewHolder = new favAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(favAdapter.ViewHolder holder, final int position) {
        Picasso.get().load(favorite.get(position).getUrl()).into(holder.album);
        holder.name.setText(favorite.get(position).getName());
        String abc = favorite.get(position).getName();
        String a = abc;
        sharedpreferences_fb = context.getApplicationContext().getSharedPreferences("facebook", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences_fb.edit();
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("positionss", position);
//                if(player_adapter.mediaPlayer==null) {
//                    mediaPlayer=new MediaPlayer();
//                }else {
//                    mediaPlayer = player_adapter.mediaPlayer;
//                }
                 favorite_list.clicked = "favorite";
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    mediaPlayer = MediaPlayer.create(view.getContext(), Uri.parse(favorite.get(position).getAudiourl()));
//                    finalTime = mediaPlayer.getDuration();
//                    startTime = mediaPlayer.getCurrentPosition();

                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mediaPlayer = MediaPlayer.create(context, Uri.parse(player_adapter.uploads.get(position + 1).getAudiourl()));
                            player_adapter.mediaPlayer.start();
                        }
                    });
                    Intent in=new Intent(context,Main4Activity.class);
                    context.startActivity(in);

                }else {
                    mediaPlayer = MediaPlayer.create(view.getContext(), Uri.parse(favorite.get(position).getAudiourl()));
                    mediaPlayer.start();
                    Intent in=new Intent(context,Main4Activity.class);
                    in.putExtra("position", position);

                    Bundle bundle = ActivityOptions.makeCustomAnimation(context, R.anim.slide_in_up, R.anim.slide_out_up).toBundle();

                    context.startActivity(in,bundle);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorite.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        ImageView album;


        public ViewHolder(View itemView) {
            super(itemView);
            album = (ImageView) itemView.findViewById(R.id.albums);
            name = (TextView) itemView.findViewById(R.id.sname);


        }


    }
}
