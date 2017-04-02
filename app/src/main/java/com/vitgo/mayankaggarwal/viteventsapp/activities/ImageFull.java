package com.vitgo.mayankaggarwal.viteventsapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.vitgo.mayankaggarwal.viteventsapp.R;
import com.vitgo.mayankaggarwal.viteventsapp.utils.Globals;
import com.google.gson.JsonObject;
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
