package com.example.mayankaggarwal.viteventsapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.mayankaggarwal.viteventsapp.activities.FacultyInformation;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

/**
 * Created by mayankaggarwal on 27/03/17.
 */

public class CustomFacultyLayout extends View {

    public Paint myLayout;

    Paint paint;
    Path path;

    public CustomFacultyLayout(Context context) {
        super(context);
    }

    public CustomFacultyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFacultyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("tagg","custom working");

        myLayout=new Paint();
        myLayout.setColor(Color.parseColor(SetTheme.colorName));
        myLayout.setAntiAlias(true);
        myLayout.setStyle(Paint.Style.FILL);

        paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        path = new Path();

        Paint mBoarderPaint = new Paint();
        mBoarderPaint.setAntiAlias(true);
        mBoarderPaint.setColor(Color.BLACK);
        mBoarderPaint.setStyle(Paint.Style.STROKE);
        mBoarderPaint.setStrokeWidth(6);

        //give inner shape color
        Paint mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.parseColor(SetTheme.colorName));
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeJoin(Paint.Join.ROUND);


        path.moveTo(0,0);
        path.lineTo(0, FacultyInformation.imageView.getLayoutParams().height);
        path.lineTo(getWidth(),180);
        path.lineTo(getWidth(), 0);
        path.lineTo(0, 0);
        canvas.drawPath(path, mInnerPaint);

    }
}