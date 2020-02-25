package com.phoenix.ulin.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.phoenix.ulin.R;

public class SignLayout extends FrameLayout {
    private ImageButton iv_save, iv_clear, iv_withdraw;
    private SignView signView;
    private Context mcontext;
    private float density;
    private OnSaveButtonListener onSaveButtonListener;

    public SignLayout(Context context) {
        this(context, null);
    }

    public SignLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        density = getResources().getDisplayMetrics().density;
        init();
    }

    private void init() {
        initSignView();
        initButton();
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void initButton() {
        iv_save = new ImageButton(mcontext);
        iv_clear = new ImageButton(mcontext);
        iv_withdraw = new ImageButton(mcontext);
        LinearLayout linearLayout = new LinearLayout(mcontext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.RIGHT);
        LinearLayout.LayoutParams llparms = new LinearLayout.LayoutParams((int) (24 * density), (int) (24 * density));
        llparms.setMargins(0, (int) (5 * density), (int) (20 * density), 0);
        iv_save.setLayoutParams(llparms);
        iv_clear.setLayoutParams(llparms);
        iv_withdraw.setLayoutParams(llparms);
        iv_save.setImageDrawable(getResources().getDrawable(R.mipmap.icon_save));
        iv_clear.setImageDrawable(getResources().getDrawable(R.mipmap.icon_clear));
        iv_withdraw.setImageDrawable(getResources().getDrawable(R.mipmap.icon_withdraw));
        linearLayout.addView(iv_clear);
        linearLayout.addView(iv_withdraw);
        linearLayout.addView(iv_save);
        FrameLayout.LayoutParams flparms = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        flparms.gravity = Gravity.RIGHT | Gravity.TOP;

        iv_save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSaveButtonListener != null) {
                    onSaveButtonListener.onSave(signView.getBitmapFromView());
                }
            }
        });

        iv_withdraw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signView.withDrawoOneStep();
            }
        });

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signView.clearStep();
            }
        });
        addView(linearLayout, flparms);
    }

    private void initSignView() {
        signView = new SignView(mcontext);
        addView(signView);
    }

    public void setOnSaveButtonListener(OnSaveButtonListener onSaveButtonListener) {
        this.onSaveButtonListener = onSaveButtonListener;
    }

    public interface OnSaveButtonListener {
        public void onSave(Bitmap bitmap);
    }
}
