package com.codesense.passengerapp.ui.agreement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codesense.passengerapp.R;
import com.codesense.passengerapp.base.BaseActivity;
import com.codesense.passengerapp.ui.home.HomeActivity;
import com.product.annotationbuilder.ProductBindView;
import com.product.process.annotation.Initialize;
import com.product.process.annotation.Onclick;

public class AgreementActivity extends BaseActivity {

    @Initialize(R.id.toolbarClose)
    ImageView toolbarClose;
    @Initialize(R.id.imgNote)
    ImageView imgNote;
    @Initialize(R.id.tvPrivacyPolicy)
    TextView tvPrivacyPolicy;
    @Initialize(R.id.tvPrivacyPolicy2)
    TextView tvPrivacyPolicy2;
    @Initialize(R.id.fbNext)
    FloatingActionButton fbNext;

    public static void start(Context context) {
        context.startActivity(new Intent(context, AgreementActivity.class));
    }

    @Override
    protected int layoutRes() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProductBindView.bind(this);
        setUIValue();
        setDynamicValue();

    }

    private void setUIValue() {
        String first = getResources().getString(R.string.agreement_first_text);
        String second = getResources().getString(R.string.agreement_second_text);
        String third = getResources().getString(R.string.agreement_third_text);
        String fourth = getResources().getString(R.string.agreement_fourth_text);
        singleTextView(tvPrivacyPolicy2, first, second, third, fourth, getResources().getColor(R.color.dark_blue_color));
    }

    private void setDynamicValue() {
        int imgIconWidth = (int) (screenWidth * 0.075);
        int imgIconHeight = (int) (screenWidth * 0.075);

        int topBottomSpace = (int) (screenHeight * 0.0089);


        RelativeLayout.LayoutParams imgLayParams = (RelativeLayout.LayoutParams) toolbarClose.getLayoutParams();
        imgLayParams.width = imgIconWidth;
        imgLayParams.height = imgIconHeight;
        toolbarClose.setLayoutParams(imgLayParams);
        ConstraintLayout.LayoutParams imgNoteLayParams = (ConstraintLayout.LayoutParams) imgNote.getLayoutParams();
        imgNoteLayParams.width = imgIconWidth*5;
        imgNoteLayParams.height = imgIconHeight*5;
        imgNote.setLayoutParams(imgNoteLayParams);
    }

        private void singleTextView(TextView textView, final String first, String second, final String third,
                                final String fourth, final int color) {
        final SpannableStringBuilder spanText = new SpannableStringBuilder();
        spanText.append(first);
        spanText.append(" ");
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        }, spanText.length() - first.length(), spanText.length(), 0);
        spanText.append(second);

        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // On Click Action
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(color);    // you can use custom color
                textPaint.setUnderlineText(false);
                textPaint.setFakeBoldText(true);
                // this remove the underline
            }
        }, spanText.length() - second.length(), spanText.length(), 0);

        spanText.append(" ");
        spanText.append(third);
        spanText.append(" ");
        spanText.append(fourth);
        spanText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // On Click Action
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(color);    // you can use custom color
                textPaint.setUnderlineText(false);
                textPaint.setFakeBoldText(true);
                // this remove the underline
            }
        }, spanText.length() - fourth.length(), spanText.length(), 0);

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spanText, TextView.BufferType.SPANNABLE);
    }

    @Onclick(R.id.toolbarClose)
    public void toolbarClose(View v) {
        finish();
    }

    @Onclick(R.id.fbNext)
    public void fbNext(View v) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
