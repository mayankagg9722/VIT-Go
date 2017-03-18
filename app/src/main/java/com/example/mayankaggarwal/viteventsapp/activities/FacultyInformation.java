package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.utils.CustomProgressDialog;
import com.example.mayankaggarwal.viteventsapp.utils.Data;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import com.squareup.picasso.Picasso;

import java.io.InputStream;

public class FacultyInformation extends AppCompatActivity {

    public static TextView name,deg,school,venue,intercom,mail,freehour;
    CardView backcard;
    ActionBar actionBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTheme.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_faculty_information);

        actionBar=getSupportActionBar();

        actionBar.setElevation(0);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        actionBar.setDisplayHomeAsUpEnabled(true);


        LinearLayout appBarLayout=(LinearLayout)findViewById(R.id.fac_appBarLayout);

        appBarLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        appBarLayout.setElevation(50);


        CardView cardView=(CardView)findViewById(R.id.fac_card);
        cardView.setCardBackgroundColor(Color.parseColor(SetTheme.colorName));

        RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.activity_faculty_information);
        relativeLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));


        name=(TextView)findViewById(R.id.profname);
        deg=(TextView)findViewById(R.id.profdeg);
        school=(TextView)findViewById(R.id.school);
        venue=(TextView)findViewById(R.id.venue);
        intercom=(TextView)findViewById(R.id.intercom);
        mail=(TextView)findViewById(R.id.email);
        freehour=(TextView)findViewById(R.id.freehour);
        backcard=(CardView)findViewById(R.id.backcard);

        backcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getFacultyData(this);

    }

    private void getFacultyData(final Activity activity) {

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                CustomProgressDialog.showProgress(FacultyInformation.this,"Fetching...");
                Data.getFacultyDetails(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        name.setText(getIntent().getStringExtra("profname"));
                        school.setText(getIntent().getStringExtra("profschool"));
                        Picasso.with(FacultyInformation.this).load("https://vitmantra.feedveed.com/facultyimages/"+
                                getIntent().getStringExtra("empid")+".jpeg").into((de.hdodenhof.circleimageview.CircleImageView)
                                findViewById(R.id.faculty_image));
//                        Data.DownloadImageTask download=new Data.DownloadImageTask((de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.faculty_image)
//                                ,FacultyInformation.this);
//                        download.execute("https://vitmantra.feedveed.com/facultyimages/"+getIntent().getStringExtra("empid")+".jpeg");
                    }
                    @Override
                    public void onFailure() {
                        CustomProgressDialog.hideProgress();
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(FacultyInformation.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
