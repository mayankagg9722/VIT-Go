package com.example.mayankaggarwal.viteventsapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.utils.Globals;

public class AboutUs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.imageone);

        Globals.cloud=0;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.cloud++;
                if (Globals.cloud == 7) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AboutUs.this);
                    builder.setTitle("Developers");
                    builder.setCancelable(false);
                    builder.setMessage("1. Navdeesh Ahuja\n2. Mayank Aggarwal\n3. Shubhanjan Chakrabarty");
                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Globals.cloud=0;
                        }
                    });
                    builder.show();
                }
            }
        });
    }
}
