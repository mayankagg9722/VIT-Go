package com.example.mayankaggarwal.viteventsapp.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mayankaggarwal.viteventsapp.R;
import com.example.mayankaggarwal.viteventsapp.RealmFiles.RealmController;
import com.example.mayankaggarwal.viteventsapp.activities.Hosteller;
import com.example.mayankaggarwal.viteventsapp.adapter.RVLateNight;
import com.example.mayankaggarwal.viteventsapp.rest.Data;
import com.example.mayankaggarwal.viteventsapp.utils.SetTheme;

/**
 * A simple {@link Fragment} subclass.
 */
public class LateNightFragment extends Fragment {


    private RecyclerView recyclerView;
    com.wang.avi.AVLoadingIndicatorView avi;
    int position;
    String permit_id;


    public LateNightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_late_night, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lateRecycler);
        avi = (com.wang.avi.AVLoadingIndicatorView) view.findViewById(R.id.loaderlate);
        avi.setIndicatorColor(Color.parseColor(SetTheme.colorName));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        getLateNight(getActivity());
        initSwipe();
        return view;
    }


    private void initSwipe() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                position = viewHolder.getAdapterPosition();
                permit_id = RealmController.with(getActivity()).getLateNight().get(position).getPvPermitID();
                cancelLateHour(getActivity(), permit_id);
                setAlert();
            }


            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = 0;
                int swipeFlags = ItemTouchHelper.LEFT;

                if (RealmController.with(getActivity()).getLateNight().get(viewHolder.getAdapterPosition())
                        .getStatus().toLowerCase().equals("pending")) {
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    return makeMovementFlags(0, 0);
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    Paint p = new Paint();

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }

                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setAlert() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        cancelLateHour(getActivity(), permit_id);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        getLateNight(getActivity());
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }


    private void getLateNight(final Activity activity) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                avi.show();
                Data.getLateNight(activity, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        recyclerView.setAdapter(new RVLateNight(RealmController.with(activity).getLateNight(), getActivity()));
                        recyclerView.setVisibility(View.VISIBLE);
                        avi.hide();
                    }

                    @Override
                    public void onFailure() {
                        avi.hide();
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void cancelLateHour(final Activity activity, final String permit_id) {
        Data.internetConnection(new Data.UpdateCallback() {
            @Override
            public void onUpdate() {
                avi.show();
                Data.cancelLateHour(activity, permit_id, new Data.UpdateCallback() {
                    @Override
                    public void onUpdate() {
                        activity.finish();
                        activity.startActivity(new Intent(activity, Hosteller.class));
                        avi.hide();
                    }

                    @Override
                    public void onFailure() {
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

}
