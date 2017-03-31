package com.example.mayankaggarwal.viteventsapp.activities;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.fragment.LateNightFragment;
import com.example.mayankaggarwal.viteventsapp.fragment.LeaveListFragment;
import com.example.mayankaggarwal.viteventsapp.fragment.OutingFragment;
import com.example.mayankaggarwal.viteventsapp.fragment.ViewPagerAdapter;
import com.example.mayankaggarwal.viteventsapp.adapter.RVLeave;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.Prefs;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

import static com.example.mayankaggarwal.viteventsapp.activities.TimeTable.viewPager;

public class Hosteller extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    LinearLayout bottom_layout;
    RelativeLayout relativeLayout;
    CardView leaveRequest, outing, lateCard;
    private ImageView leaveImage, outingImage, lateImage;
    private TextView leavetext, outingtext, latetext;
    private TextView leavesectext, outingsectext, latesectext;
    CoordinatorLayout coordinatorLayout;
    private ValueAnimator animator;
    private int cardInitialHeight = 210;
    private boolean expanded = false;
    private LinearLayout expandLayoutText;
    private TabLayout tabLayout;
    private static ViewPagerAdapter viewpageradapter;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosteller);
        init();
        setClickListener();
    }

    private void init() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.hostel_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Hosteller");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setBackgroundColor(Color.parseColor(SetTheme.colorName));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor(SetTheme.colorName));
        }


        relativeLayout = (RelativeLayout) findViewById(R.id.activity_hosteller);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.hotelcoordinate);

        relativeLayout.setBackgroundColor(Color.parseColor(SetTheme.colorName));

        leaveRequest = (CardView) findViewById(R.id.leaverequest);
        outing = (CardView) findViewById(R.id.outing);
        lateCard = (CardView) findViewById(R.id.lateCard);
        leaveImage = (ImageView) findViewById(R.id.leaveImage);
        lateImage = (ImageView) findViewById(R.id.lateimage);
        outingImage = (ImageView) findViewById(R.id.outingImage);
        leavetext = (TextView) findViewById(R.id.leavetext);
        latetext = (TextView) findViewById(R.id.latetext);
        outingtext = (TextView) findViewById(R.id.outingtext);
        leavesectext = (TextView) findViewById(R.id.leavesectext);
        latesectext = (TextView) findViewById(R.id.latesectext);
        outingsectext = (TextView) findViewById(R.id.outingsectext);
        expandLayoutText = (LinearLayout) findViewById(R.id.expandlayout);

        leaveRequest.getLayoutParams().height=210;
        outing.getLayoutParams().height=210;
        lateCard.getLayoutParams().height=210;

        ((TextView) findViewById(R.id.name)).setText(Prefs.getPrefs("name", this).split(" ")[0].substring(0,1).toUpperCase()
                +Prefs.getPrefs("name", this).split(" ")[0].substring(1).toLowerCase());

        bottomSheetBehavior = (BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_item)));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottom_layout = (LinearLayout) findViewById(R.id.bottom_sheet_item);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        bottom_layout.getLayoutParams().height = height - toolbar.getHeight();
        bottom_layout.requestLayout();
        bottomSheetBehavior.onLayoutChild(coordinatorLayout, bottom_layout, ViewCompat.LAYOUT_DIRECTION_LTR);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.leaveviewpager);
        viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewpageradapter.addfragment(new LeaveListFragment(), "Leave Request");
        viewpageradapter.addfragment(new OutingFragment(), "Outing Request");
        viewpageradapter.addfragment(new LateNightFragment(), "Late Request");
        viewPager.setAdapter(viewpageradapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }


    private void setClickListener() {
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {

                    case BottomSheetBehavior.STATE_COLLAPSED:

                        expanded = false;

                        setFadeAnimation(latetext, leaveImage, 1);
                        setFadeAnimation(leavetext, lateImage, 1);
                        setFadeAnimation(outingtext, outingImage, 1);

                        setFadeAnimation(tabLayout, viewPager, 0);


                        break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:


                        expanded = true;

                        setFadeOutAnimation(leaveImage, leavetext, 0);
                        setFadeOutAnimation(lateImage, latetext, 0);
                        setFadeOutAnimation(outingImage, outingtext, 0);

                        setFadeOutAnimation(tabLayout, viewPager, 1);


                        break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;

                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        leaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expanded) {
                    Intent intent = new Intent(Hosteller.this, LeaveRequest.class);
                    startActivity(intent);
                }

            }
        });

        outing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expanded) {
                    Intent intent = new Intent(Hosteller.this, OutingRequest.class);
                    startActivity(intent);
                }
            }
        });

        lateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expanded) {
                    Intent intent = new Intent(Hosteller.this, LateNightRequest.class);
                    startActivity(intent);
                }
            }
        });


    }

    private void setLayoutExpandAnimation(final CardView cardview) {
        animator = ValueAnimator.ofInt(cardview.getLayoutParams().height, cardInitialHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = cardview.getLayoutParams();
                layoutParams.height = val;
                cardview.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private void setLayoutCompressAnimation(final CardView cardview) {
        animator = ValueAnimator.ofInt(cardview.getLayoutParams().height, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = cardview.getLayoutParams();
                layoutParams.height = val;
                cardview.setLayoutParams(layoutParams);
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private void setFadeOutAnimation(final ImageView imageView, final TextView textView, final int visible) {
        Animation fadeout = new AlphaAnimation(1f, 0f);
        fadeout.setDuration(300);

        imageView.startAnimation(fadeout);
        textView.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (visible == 1) {
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }

                setLayoutCompressAnimation(lateCard);
                setLayoutCompressAnimation(leaveRequest);
                setLayoutCompressAnimation(outing);


            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

        });
    }

    private void setFadeOutAnimation(final TabLayout textView, final ViewPager viewPager, final int visible) {

        final Animation fadeout = new AlphaAnimation(1f, 0f);
        fadeout.setDuration(600);

        textView.startAnimation(fadeout);
        viewPager.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (visible == 1) {
                    textView.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    textView.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

                } else {
                    textView.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

    }

    private void setFadeAnimation(final TabLayout textview, final ViewPager viewPager, final int visible) {

        Animation fadeout = new AlphaAnimation(0f, 1f);
        fadeout.setDuration(100);

        textview.startAnimation(fadeout);
        textview.setSelectedTabIndicatorColor(Color.parseColor("#F5F5F5"));
        viewPager.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (visible == 1) {
                    textview.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                } else {
                    textview.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                }
                setLayoutExpandAnimation(lateCard);
                setLayoutExpandAnimation(leaveRequest);
                setLayoutExpandAnimation(outing);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }


    private void setFadeAnimation(final TextView textview, final ImageView imageView, final int visible) {
        Animation fadeout = new AlphaAnimation(0f, 1f);
//        fadeout.setInterpolator(new DecelerateInterpolator());
        fadeout.setDuration(200);

        imageView.startAnimation(fadeout);
        textview.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (visible == 1) {
                    imageView.setVisibility(View.VISIBLE);
                    textview.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                    textview.setVisibility(View.GONE);
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
    }

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
