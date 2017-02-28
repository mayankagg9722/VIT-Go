package com.example.mayankaggarwal.viteventsapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mayankaggarwal.viteventsapp.utils.Prefs;

import java.io.IOException;

public class ImageGallery extends AppCompatActivity {


    ActionBar actionBar;
    Button selectimage, setimage;

    private static final int img=1;

    de.hdodenhof.circleimageview.CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

//        actionBar = getSupportActionBar();
        selectimage = (Button) findViewById(R.id.imageselect);
        setimage = (Button) findViewById(R.id.setimage);

        imageView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.myimage);


//        actionBar.setTitle("Image");
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f37051")));

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),img);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==img&&resultCode==RESULT_OK && data !=null && data.getData()!=null) {
            data.getData();
            //Log.e("uridata",data.getData().toString());
            if (data.getData() != null) {
                //Log.v("uri",data.getData().toString());
                Bitmap photo = null;
                try {
                    Prefs.setPrefs("profileimage",data.getData().toString(),this);
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                            , data.getData());
                    photo = getResizedBitmap(photo, 900, 1200);
                    imageView.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }
}
