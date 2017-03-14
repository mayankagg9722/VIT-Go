package com.example.mayankaggarwal.viteventsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;

import com.example.mayankaggarwal.viteventsapp.utils.Data;

import com.example.mayankaggarwal.viteventsapp.utils.Globals;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;


    AppBarLayout appBarLayout;
    ActionBarDrawerToggle mActionDrawerToggle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SetTheme.setThemePref(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        appBarLayout=(AppBarLayout)findViewById(R.id.appBarLayout);
        appBarLayout.setBackground(new ColorDrawable(Color.parseColor(SetTheme.colorName)));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.rrv_swipe_refresh_layout);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetching Attendance");
        progressDialog.setMessage("Loading..");
        progressDialog.create();
        progressDialog.setCancelable(false);

        Realm.init(this);

//        final Realm realm = Realm.getDefaultInstance();

        //update date and attendance
        updateDayAndDate();

        //fetch attendance
        fetchAttendance(this);

        updateFaculties(this);


        getSupportActionBar().setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RVAttendaceList(RealmController.with(this).getAtendance(), this, true));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Globals.doneFetching=0;
                fetchAttendance(MainActivity.this);
            }
        });

//        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//               ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//        toggle.setDrawerIndicatorEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.navvv);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawer.openDrawer(GravityCompat.START);
//            }
//        });
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();


//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        View header = navigationView.getHeaderView(0);
//
//        TextView name = (TextView) header.findViewById(R.id.nametext);
//        profile=(de.hdodenhof.circleimageview.CircleImageView)header.findViewById(R.id.profile_image);
//        TextView regno = (TextView) header.findViewById(R.id.regtext);
//
//        name.setText(Prefs.getPrefs("name", this));
//        regno.setText(Prefs.getPrefs("regno", this));
//
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,ImageGallery.class));
//            }
//        });

////        Log.d("tagg","id:"+Prefs.getPrefs("profileimage",this));
//        if(Prefs.getPrefs("readPermission",this).equals("1")){
//            setNavImage();
//        }

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

         mActionDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.navvv);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerLayout.setDrawerListener(mActionDrawerToggle);
        mActionDrawerToggle.syncState();

        navigation_drawer.nav_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

    }


//    private void setNavImage() {
//        try {
//            Prefs.setPrefs("profileimage",Prefs.getPrefs("profileimage",this),this);
//            Bitmap photo = null;
//            photo = MediaStore.Images.Media.getBitmap(getContentResolver()
//                    , Uri.parse(Prefs.getPrefs("profileimage",this)));
////            photo = getResizedBitmap(photo, 400, 400);
//            profile.setImageBitmap(photo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
//        int width = bm.getWidth();
//        int height = bm.getHeight();
//        float scaleWidth = ((float) newWidth) / width;
//        float scaleHeight = ((float) newHeight) / height;
//        // CREATE A MATRIX FOR THE MANIPULATION
//        Matrix matrix = new Matrix();
//        // RESIZE THE BIT MAP
//        matrix.postScale(scaleWidth, scaleHeight);
//
//        // "RECREATE" THE NEW BITMAP
//        Bitmap resizedBitmap = Bitmap.createBitmap(
//                bm, 0, 0, width, height, matrix, false);
//        bm.recycle();
//        return resizedBitmap;
//    }


    private void updateDayAndDate() {

        TextView main_date = (TextView) findViewById(R.id.main_date);
        Date date = new Date();
        SimpleDateFormat dat = new SimpleDateFormat("dd MMMM,");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        main_date.setText(dat.format(date).toString() + " " + day.format(date).toString());

    }

    private void fetchAttendance(final Activity activity) {

//            Log.d("tagg","attendance");
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                if (Globals.doneFetching == 0) {
                    progressDialog.show();
                    Globals.doneFetching = 1;
                    Data.updateAttendance(activity, new Data.UpdateCallback() {
                        @Override
                        public void onUpdate() {
//                    Log.d("tagg","success api");
                            recyclerView.setAdapter(new RVAttendaceList(RealmController.with(activity).getAtendance(), MainActivity.this, true));
                            updateFaculties(activity);
                        }
                        @Override
                        public void onFailure() {
                            swipeRefreshLayout.setRefreshing(false);
                            if(Globals.doneFetching==1){
                                progressDialog.dismiss();
                            }
//                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
//                    Log.d("tagg","fail api");
                        }
                    });
                }
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFaculties(final Activity activity) {

        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                Data.updateFaculty(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
//                    Log.d("tagg","success api");
                        swipeRefreshLayout.setRefreshing(false);
                        if(Globals.doneFetching==1){
                            progressDialog.dismiss();
                        }

                    }
                    @Override
                    public void onFailure() {
                            swipeRefreshLayout.setRefreshing(false);
                        if(Globals.doneFetching==1){
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


//        if (InternetConnection.isNetworkAvailable()) {
//            if (Globals.doneFetching == 0) {
//                progressDialog.show();
//                Globals.doneFetching = 1;
//                Data.updateAttendance(this, new Data.UpdateCallback() {
//                    @Override
//                    public void onUpdate() {
////                    Log.d("tagg","success api");
//                        recyclerView.setAdapter(new RVAttendaceList(RealmController.with(activity).getAtendance(), MainActivity.this, true));
//
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onFailure() {
//                        swipeRefreshLayout.setRefreshing(false);
////                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
////                    Log.d("tagg","fail api");
//                    }
//                });
//            }
//        } else {
//            swipeRefreshLayout.setRefreshing(false);
//            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }
//        if (Globals.doneFetching == 1) {
//            progressDialog.dismiss();
//        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//            startActivity(new Intent(this, TimeTable.class));
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//            RealmController.with(this).clearAll();
//            Prefs.deletePrefs(this);
//            finish();
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

//    @Override
//    protected void onRestart() {
//        if(Prefs.getPrefs("readPermission",this).equals("1")){
//            setNavImage();
//        }
//        super.onRestart();
//    }

}
