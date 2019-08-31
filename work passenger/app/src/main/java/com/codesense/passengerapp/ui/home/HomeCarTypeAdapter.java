package com.codesense.passengerapp.ui.home;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;

public class HomeCarTypeAdapter extends RecyclerView.Adapter<HomeCarTypeAdapter.ViewHolder> {


    private Activity activity;
    private int width,height;
    public HomeCarTypeAdapter(Activity activity,int width,int height){
        this.activity = activity;
        this.width = width;
        this.height = height;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item_for_cab, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCarType;
        private TextView tvMins,tvTypeOfCab,tvPrice;
        //int position;

        public ViewHolder(View view) {
            super(view);
            tvMins = view.findViewById(R.id.tvMins);
            imgCarType = view.findViewById(R.id.imgCarType);
            tvTypeOfCab = view.findViewById(R.id.tvTypeOfCab);
            tvPrice = view.findViewById(R.id.tvPrice);

            int imgIconWidth = (int) (width * 0.070);
            int imgIconHeight = (int) (width * 0.070);

            RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) imgCarType.getLayoutParams();
            imgLayParams.width = imgIconWidth;
            imgLayParams.height = imgIconHeight;
            imgCarType.setLayoutParams(imgLayParams);
        }
    }
}
