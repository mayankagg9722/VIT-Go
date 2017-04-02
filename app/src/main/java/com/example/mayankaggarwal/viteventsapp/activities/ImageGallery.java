package com.example.mayankaggarwal.viteventsapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.utils.ImagePath;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import java.io.IOException;

public class ImageGallery extends AppCompatActivity {


    private static final int REQUEST_PERMISSION =1 ;

    ActionBar actionBar;

    CardView selectimage;

    private static final int img=1;

    de.hdodenhof.circleimageview.CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SetTheme.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_image_gallery);

        init();

        setClickListener();

    }

    private void setClickListener() {

        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(ImageGallery.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ImageGallery.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION);
                    return;
                }
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),img);
            }
        });


    }

    private void init() {
        actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));
        actionBar.setTitle("Image");
        actionBar.setDisplayHomeAsUpEnabled(true);

        selectimage = (CardView) findViewById(R.id.imageselect);

        selectimage.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));


        imageView = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.myimage);

        if(!(Prefs.getPrefs("profileimage",this).equals("notfound"))){
            if(Prefs.getPrefs("readPermission",this).equals("1")){
                try {
                    String path=ImagePath.getPath(this,Uri.parse(Prefs.getPrefs("profileimage",this)));
                    if (path != null) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(path);
                        imageView.setImageBitmap(myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    String path=ImagePath.getPath(this,Uri.parse(Prefs.getPrefs("profileimage",this)));
//                    Log.d("tagg","path:"+path);
                    if (path != null) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(path);
                        imageView.setImageBitmap(myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            imageView.setImageResource(R.drawable.unknown);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                Prefs.setPrefs("readPermission","1",this);
            } else {
                // User refused to grant permission.
                Prefs.setPrefs("readPermission","0",this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==img&&resultCode==RESULT_OK && data !=null && data.getData()!=null) {
            if (data.getData() != null) {
                //Log.v("uri",data.getData().toString());
                Bitmap photo = null;
                try {
                    Prefs.setPrefs("profileimage",data.getData().toString(),this);
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                            , data.getData());
//                  photo = getResizedBitmap(photo, 900, 900);
                    imageView.setImageBitmap(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // CREATE A MATRIX FOR THE MANIPULATION
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(
//                bm, 0, 0, width, height, matrix, false);
//        bm.recycle();
//        return resizedBitmap;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
