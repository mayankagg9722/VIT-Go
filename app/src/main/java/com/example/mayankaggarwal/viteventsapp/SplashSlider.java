package com.example.mayankaggarwal.viteventsapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.mayankaggarwal.viteventsapp.fragment.LoginFragment;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.heinrichreimersoftware.materialintro.slide.Slide;


public class SplashSlider extends IntroActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        boolean fullscreen =  true;
        boolean scrollable =  true;
        boolean customFragments =  true;
//      boolean permissions = true;
        boolean showBack = true;
        boolean showNext = true;
        boolean skipEnabled = true;
        boolean finishEnabled =  true;
        boolean getStartedEnabled =  false;

        setFullscreen(fullscreen);

        super.onCreate(savedInstanceState);

        setButtonBackFunction(skipEnabled ? BUTTON_BACK_FUNCTION_SKIP : BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction(finishEnabled ? BUTTON_NEXT_FUNCTION_NEXT_FINISH : BUTTON_NEXT_FUNCTION_NEXT);
        setButtonBackVisible(showBack);
        setButtonNextVisible(showNext);
        setButtonCtaVisible(getStartedEnabled);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_TEXT);

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_metaphor)
                .description(R.string.description_material_metaphor)
                .image(R.drawable.sample)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .scrollable(scrollable)
                .buttonCtaLabel("Know More")
                .buttonCtaClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(SplashSlider.this, R.string.toast_button_cta, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        nextSlide();
                    }
                })
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_bold)
                .description(R.string.description_material_bold)
                .image(R.drawable.sample)
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .scrollable(scrollable)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_motion)
                .description(R.string.description_material_motion)
                .image(R.drawable.sample)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .scrollable(scrollable)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.title_material_shadow)
                .description(R.string.description_material_shadow)
                .image(R.drawable.sample)
                .background(R.color.color_material_shadow)
                .backgroundDark(R.color.color_dark_material_shadow)
                .scrollable(scrollable)
                .build());

//        final Slide permissionsSlide;
//        if (permissions) {
//            permissionsSlide = new SimpleSlide.Builder()
//                    .title(R.string.title_permissions)
//                    .description(R.string.description_permissions)
//                    .background(R.color.color_permissions)
//                    .backgroundDark(R.color.color_dark_permissions)
//                    .scrollable(scrollable)
//                    .permissions(new String[]{Manifest.permission.CAMERA,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE})
//                    .build();
//            addSlide(permissionsSlide);
//        } else {
//            permissionsSlide = null;
//        }

        final Slide loginSlide;
        if (customFragments) {
            loginSlide = new FragmentSlide.Builder()
                    .background(R.color.color_custom_fragment_1)
                    .backgroundDark(R.color.color_dark_custom_fragment_1)
                    .fragment(LoginFragment.newInstance())
                    .build();
            addSlide(loginSlide);

        } else {
            loginSlide = null;
        }

        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override
            public void onNavigationBlocked(int position, int direction) {
                View contentView = findViewById(android.R.id.content);
                if (contentView != null) {
                    Slide slide = getSlide(position);
//                    if (slide == permissionsSlide) {
//                        Snackbar.make(contentView, R.string.label_grant_permissions, Snackbar.LENGTH_LONG).show();
//                    } else
                    if (slide == loginSlide) {
                        Snackbar.make(contentView, R.string.label_fill_out_form, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

}
