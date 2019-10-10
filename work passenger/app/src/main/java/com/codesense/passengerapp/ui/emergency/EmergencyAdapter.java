package com.codesense.passengerapp.ui.emergency;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.ui.socialaccount.SocialAccountActivity;

import java.util.List;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder>  {


    Activity activity;
    int width, height;



    public EmergencyAdapter(Activity activity, int w, int h) {
        this.activity = activity;
        this.width = w;
        this.height = h;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_emergency_contact, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlEmergency;
        private TextView tvEmergencyName,tvEmergencyPhone,tvEmergencyDesc;
        SwitchCompat saveContactSwitchCompat;
        //int position;

        public ViewHolder(View view) {
            super(view);
            rlEmergency = view.findViewById(R.id.rlEmergency);
            tvEmergencyName = view.findViewById(R.id.tvEmergencyName);
            tvEmergencyPhone = view.findViewById(R.id.tvEmergencyPhone);
            tvEmergencyDesc = view.findViewById(R.id.tvEmergencyDesc);
            saveContactSwitchCompat = view.findViewById(R.id.saveContactSwitchCompat);
        }
    }
}
