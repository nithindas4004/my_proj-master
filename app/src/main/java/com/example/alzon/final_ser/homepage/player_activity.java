package com.example.alzon.final_ser.homepage;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cleveroad.audiowidget.AudioWidget;
import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.demo.Main4Activity;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class player_activity extends Main5Activity {
    public String DATABASE_PATH_UPLOADSimages = "images/";
    public static Bitmap bt;
    private List<Upload> playerlist;
    GridLayoutManager grd;
    public static CardView crd;
    public static CardView crdview;
    MediaPlayer mediaPlayer;
    int ps;
    public static ImageView pausee;
    public static ProgressDialog progressDialog;
    RecyclerView rcv;
    private DatabaseReference mDatabase;
    SharedPreferences sh;
    String url, picurl, nameurl;

    int position;
    public static ImageView img;
    private RecyclerView.Adapter adap;
    ProgressBar progress;
    public static ProgressBar progress5;
    private static final int OVERLAY_PERMISSION_REQ_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        }

        rcv = findViewById(R.id.play);
        img = (ImageView) findViewById(R.id.playing);
        crd = (CardView) findViewById(R.id.cardv);
        crdview = findViewById(R.id.cardView);
        pausee = (ImageView) findViewById(R.id.pause);
        ImageView banner = (ImageView) findViewById(R.id.banner);
        TextView txt = (TextView) findViewById(R.id.albumname);
        Intent in = getIntent();
        setupWindowAnimations();
        progress = findViewById(R.id.progressBar2);
        progress5 = findViewById(R.id.progressBar5);
        progress5.setVisibility(View.GONE);
        if(player_adapter.count==1)
        {
            progress5.setVisibility(View.VISIBLE);

        }
        Bundle bn = in.getExtras();
        sh = player_activity.this.getApplicationContext().getSharedPreferences("facebook", Context.MODE_PRIVATE);
        picurl = sh.getString("pic", null);
        nameurl = sh.getString("nam", null);
        ps = sh.getInt("position", 0);
        //Log.v("dfdf",bt.toString());
        crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(player_activity.this, Main4Activity.class);
                intent.putExtra("position", position);

                Bundle bundle = ActivityOptions.makeCustomAnimation(player_activity.this, R.anim.slide_in_up, R.anim.slide_out_up).toBundle();
                startActivity(intent, bundle);
                // Log.v("dfdf",bt.toString());
                new GetImageFromURL(bt, player_adapter.uploads).execute();

                //  NotificationGenerator.customBigNotification(getApplication());

            }
        });


        if (player_adapter.mediaPlayer == null) {

            crd.setVisibility(View.GONE);
        } else {
            if (player_adapter.mediaPlayer != null) {
                crd.setVisibility(View.VISIBLE);
            }
        }

        if (bn != null) {
            url = bn.getString("textvalue");
            position = bn.getInt("position", 0);
            DATABASE_PATH_UPLOADSimages = "images/" + url + "/songs";
        }

        //Picasso.get().load(tamil_adapter.uploads.get(position).getBannerurl()).into(banner);

        Picasso.get().load(picurl).into(img);

        txt.setText(tamil_adapter.uploads.get(position).getName());
        Picasso.get()
                .load(tamil_adapter.uploads.get(position).getBannerurl())
                .into(banner, new Callback() {
                    @Override
                    public void onSuccess() {
                        progress.setVisibility(View.GONE);
                        crdview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }


                });

        progressDialog = new ProgressDialog(this);

        playerlist = new ArrayList<>();


        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADSimages);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    playerlist.add(upload);

                }
                grd = new GridLayoutManager(player_activity.this, 1);
                rcv.setLayoutManager(grd);

                adap = new player_adapter(getApplicationContext(), playerlist);
                rcv.setAdapter(adap);
                adap.notifyDataSetChanged();
                // progress.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public class GetImageFromURL extends AsyncTask<Void, Void, Bitmap> {

        public Bitmap bt;
        public List<Upload> uploads;

        public GetImageFromURL(Bitmap bt, List<Upload> uploads) {
            this.bt = bt;

            this.uploads = uploads;
        }

        @Override

        protected Bitmap doInBackground(Void... voids) {
            // Bitmap bt=getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/final-b9f81.appspot.com/o/images%2F1505451438843.jpeg?alt=media&token=d5801ef5-1128-49a2-96e2-32ca7606f770");

            return bt;
            //   }

        }


        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);
//Bitmap bt=getBitmapFromURL("https://firebasestorage.googleapis.com/v0/b/final-b9f81.appspot.com/o/images%2F1505451438843.jpeg?alt=media&token=d5801ef5-1128-49a2-96e2-32ca7606f770");
            BitmapDrawable back = new BitmapDrawable(getResources(), bmp);
            bt = getBitmapFromURL(uploads.get(ps).albumurl);
            Log.v("dsfsdf", String.valueOf(position));
            Log.v("sdfgdfg", String.valueOf(bt));
            //GaussianBlur.with(MainActivity.this).size(300).radius(10).put(bmp, imgV);
        }

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                // now you can show audio widget
            }
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

    }
};


