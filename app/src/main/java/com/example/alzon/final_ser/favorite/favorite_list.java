package com.example.alzon.final_ser.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.alzon.final_ser.R;
import com.example.alzon.final_ser.database.DatabaseHelper;
import com.example.alzon.final_ser.modelclass.favorite_modelclass;
import com.example.alzon.final_ser.startpage.start_page;

import java.util.ArrayList;
import java.util.List;

public class favorite_list extends AppCompatActivity {
    private List<favorite_modelclass> favorite;
    RecyclerView recyclerView;
    GridLayoutManager lvLayoutManager;
    favAdapter ap;
    public static String clicked;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.favrecy);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("Favorite");
        mTopToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
clicked="favorite";
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), start_page.class));
                finish();

            }
        });
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        favorite = new ArrayList<>();
//        ArrayList<String> values = new ArrayList<String>();
//        HashSet<favorite_modelclass> hashSet = new HashSet<favorite_modelclass>();
//
//        favorite_list.addAll(db.getfavorite());
//        Set<favorite_modelclass> removeduplicate = new LinkedHashSet<favorite_modelclass>(favorite_list);

        // now let's clear the ArrayList so that we can copy all elements from LinkedHashSet
        favorite.clear();

        // copying elements but without any duplicates
        favorite.addAll(db.getfavorite());

                List<favorite_modelclass> duplicate = new ArrayList<favorite_modelclass>();

        for (favorite_modelclass event : favorite) {
            boolean isFound = false;
            // check if the event name exists in noRepeat
            for (favorite_modelclass e : duplicate) {
                if (e.getName().equals(event.getName()) || (e.equals(event))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) duplicate.add(event);
        }
        lvLayoutManager = new GridLayoutManager(favorite_list.this, 1);
        recyclerView.setLayoutManager(lvLayoutManager);

        ap = new favAdapter(favorite_list.this, duplicate);
        recyclerView.setAdapter(ap);


    }
}
