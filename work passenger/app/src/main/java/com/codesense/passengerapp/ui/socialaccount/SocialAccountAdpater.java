package com.codesense.passengerapp.ui.socialaccount;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.ui.helper.Utils;

import java.util.List;

public class SocialAccountAdpater extends RecyclerView.Adapter<SocialAccountAdpater.ViewHolder> {


    Activity activity;
    int width, height;
    List<SocialAccountActivity.SocialActionInfo> socialActionInfoList;

    public SocialAccountAdpater(Activity activity, List<SocialAccountActivity.SocialActionInfo> socialActionInfoList,
                                int w, int h) {
        this.activity = activity;
        this.socialActionInfoList = socialActionInfoList;
        this.width = w;
        this.height = h;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_social, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SocialAccountActivity.SocialActionInfo socialActionInfo = socialActionInfoList.get(position);
        String contentTitle = socialActionInfo.getActionName();

        int actionImage = Utils.GetInstance().getDrawableResourceID(activity, socialActionInfo.getIconName());
        holder.tvText.setText(contentTitle);
        holder.imgIcon.setImageDrawable(activity.getResources().getDrawable(actionImage));

    }

    @Override
    public int getItemCount() {
        return socialActionInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_facebook;
        private ImageView imgIcon, imgRight;
        private TextView tvText;
        View View;
        //int position;

        public ViewHolder(View view) {
            super(view);
            rl_facebook = view.findViewById(R.id.rl_facebook);
            imgIcon = view.findViewById(R.id.imgIcon);
            tvText = view.findViewById(R.id.tvText);
            imgRight = view.findViewById(R.id.imgRight);
            View = view.findViewById(R.id.view);

            int imgIconWidth = (int) (width * 0.085);
            int imgIconHeight = (int) (width * 0.085);
            int arrowWidth = (int) (width * 0.070);
            int arrowHeight = (int) (width * 0.070);

            LinearLayout.LayoutParams imgLayParams = (LinearLayout.LayoutParams) imgIcon.getLayoutParams();
            imgLayParams.width = imgIconWidth;
            imgLayParams.height = imgIconHeight;
            imgIcon.setLayoutParams(imgLayParams);


            RelativeLayout.LayoutParams imgLayParams1 = (RelativeLayout.LayoutParams) imgRight.getLayoutParams();
            imgLayParams1.width = arrowWidth;
            imgLayParams1.height = arrowHeight;
            imgRight.setLayoutParams(imgLayParams1);

        }
    }

}
