
package com.example.alzon.final_ser.homepage;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.modelclass.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class homepage extends Main5Activity {
    private RecyclerView recyclerView;
    private TextView tx;
    public static final String DATABASE_PATH_UPLOADSimages = "images";
    //adapter object
    private RecyclerView.Adapter adapter,Myadapter;
ImageView flipp;
    //database reference
    private DatabaseReference mDatabase, tdatabase;
Toolbar mTopToolbar;
    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Upload> malayalam, tam;
    private RecyclerView mRecyclerView, tamil;
    private LinearLayoutManager malayalamlayout, tamillayout;
    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle mToggle;
    //FirebaseAuth mAuth;
    // FirebaseAuth.AuthStateListener mAuthListner;

    // @Override
    //protected void onStart() {
    //  super.onStart();
    //mAuth.addAuthStateListener(mAuthListner);
    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setSupportActionBar(mTopToolbar);
        setupWindowAnimations();
//        getSupportActionBar().setTitle("Latest Malayalam Hits");
//        mTopToolbar.setTitleTextColor(Color.WHITE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), homepage.class));
//                finish();
//            }
//        });


        mRecyclerView = (RecyclerView) findViewById(R.id.malayalam_items);
//        tamil=(RecyclerView) findViewById(R.id.Tamil_list);
//ViewFlipper vf=findViewById(R.id.view);
//        malayalamlayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerView.setLayoutManager(malayalamlayout);
//       tamillayout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//         tamil.setLayoutManager(tamillayout);
//        vf.setAutoStart(true);
//        vf.setFlipInterval(2000);
//        vf.startFlipping();


        progressDialog = new ProgressDialog(this);

        malayalam = new ArrayList<>();
      //  tam = new ArrayList<>();


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
                    malayalam.add(upload);

                }
//                tdatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADSimages);
//
//                //adding an event listener to fetch values
//                tdatabase.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        //dismissing the progress dialog
//                        progressDialog.dismiss();
//
//                        //iterating through all the values in database
//                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                            Upload upload = postSnapshot.getValue(Upload.class);
//                            tam.add(upload);
//
//                        }


                        //creating adaptermalayalam
                        adapter = new tamil_adapter(getApplicationContext(), malayalam);
                        //creating adapter tamil
//                        Myadapter=new tamil_adapter(getApplicationContext(), tam);


RecyclerView.LayoutManager mlayout=new GridLayoutManager(homepage.this,3);
mRecyclerView.setLayoutManager(mlayout);
                        //adding adapter to malayalam recyclerview
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        //adding adapter to tamil recyclerview
//                         tamil.setAdapter(Myadapter);
//                         Myadapter.notifyDataSetChanged();


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });

            }


    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }




    }
