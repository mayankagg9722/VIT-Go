package com.example.mayankaggarwal.viteventsapp.activities;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.models.EventList;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.google.gson.JsonObject;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);

        JsonObject e= Globals.register_event;

        ImageView imageView=(ImageView)findViewById(R.id.fullimage);

        Picasso.with(this).load("https://vitmantra.feedveed.com/posters/"+e.get("_id").getAsString()).into(imageView);

    }
}
