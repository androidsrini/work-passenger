package com.codesense.passengerapp.ui.socialaccount;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.helper.Utils;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

import java.util.ArrayList;
import java.util.List;

public class SocialAccountActivity extends BaseActivity {

    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.tvTitle)
    TextView tvTitle;
    @Initialize(R.id.tvChooseAccount)
    TextView tvChooseAccount;
    @Initialize(R.id.view)
    View view;
    @Initialize(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final int SOCIAL_ACCOUNT_NAME_INDEX = 0;
    private static final int SOCIAL_ACCOUNT_ICON_NAME_INDEX = 1;
    private List<SocialActionInfo> socialAccountActionInfos = new ArrayList<>();


    SocialAccountAdpater adapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_social_account;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setUI();
        setDynamicValue();
    }

    private void setDynamicValue() {

        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgIconHeight = (int) (screenWidth * 0.075);

        int topBottomSpace = (int) (screenHeight * 0.0089);

        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);

        ConstraintLayout.LayoutParams tvChooseAccountLayout = (ConstraintLayout.LayoutParams) tvChooseAccount.getLayoutParams();
        tvChooseAccountLayout.setMargins(topBottomSpace * 2, topBottomSpace * 5, 0, 0);
        tvChooseAccount.setLayoutParams(tvChooseAccountLayout);

        ConstraintLayout.LayoutParams viewLayout = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        viewLayout.setMargins(0, topBottomSpace * 3, 0, 0);
        view.setLayoutParams(viewLayout);


    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SocialAccountActivity.class));
    }

    private void setUI() {

        int storeInfoActionListSize = getResources().getInteger(R.integer.social_account_count);
        List<TypedArray> typedArrayList = Utils.getMultiTypedArray(this, Utils.SOCIAL_ACCOUNT_ACTION);
        if (storeInfoActionListSize > typedArrayList.size()) {
            storeInfoActionListSize = typedArrayList.size();
        }
        for (int index = 0; index < storeInfoActionListSize; index++) {
            TypedArray typedArray = typedArrayList.get(index);
            String name = typedArray.getString(SOCIAL_ACCOUNT_NAME_INDEX);
            String iconName = typedArray.getString(SOCIAL_ACCOUNT_ICON_NAME_INDEX);
            socialAccountActionInfos.add(new SocialActionInfo(name, iconName));
        }

        recyclerView.setAdapter(new SocialAccountAdpater(this, socialAccountActionInfos, screenWidth, screenHeight));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public class SocialActionInfo {
        String actionName;
        String iconName;

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public String getIconName() {
            return iconName;
        }

        public void setIconName(String iconName) {
            this.iconName = iconName;
        }

        private SocialActionInfo(String actionName, String iconName) {
            this.actionName = actionName;
            this.iconName = iconName;
        }
    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }


}
