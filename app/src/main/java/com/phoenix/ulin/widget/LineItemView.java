package com.phoenix.ulin.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.blankj.utilcode.util.LogUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.databinding.ViewLineItemBinding;
import com.ulin.baselib.utils.CommonUtils;
import com.ulin.baselib.utils.DrawableUtils;


/**
 *
 */

public class LineItemView extends FrameLayout {

    ViewLineItemBinding binding;
    private int bottomLineSize;
    private int bottomLineColor;
    private Paint paint;
    private OnValueChangedListener listener;
    private String name;

    public LineItemView(Context context) {
        this(context, null);
    }

    public LineItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_line_item, this, true);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineItemView);
        setText(a.getString(R.styleable.LineItemView_li_text));
        setLeftIcon(a.getResourceId(R.styleable.LineItemView_li_left_icon, 0));
        setRightIcon(a.getResourceId(R.styleable.LineItemView_li_right_icon, 0));
        setName(a.getString(R.styleable.LineItemView_li_name));
        if (!isInEditMode()) {
            setLeftRightPadding(a.getDimensionPixelSize(R.styleable.LineItemView_li_left_right_padding, CommonUtils.Dp2Px(context, 6)));
        }
        bottomLineSize = a.getDimensionPixelSize(R.styleable.LineItemView_li_bottom_line_size, 0);
        bottomLineColor = a.getColor(R.styleable.LineItemView_li_bottom_line_color, getResources().getColor(R.color.table_view_default_selected_background_color));
        a.recycle();

        int dp10 = CommonUtils.Dp2Px(context, 10);
        int dp15 = CommonUtils.Dp2Px(context, 15);
        setWillNotDraw(false);
        setPadding(dp10, dp15, dp10, dp15);

        binding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    LogUtils.e("空值");
                } else {
                    setName(editable.toString());
                    name = editable.toString();
                    if (listener != null) {
                        listener.onValueChanged();
                    }
                }
            }
        });

    }


    public void setName(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            binding.etName.setText(text);
        }
    }

    public void setName(@StringRes int text) {
        setName(getResources().getString(text));
    }

    private void setText(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            binding.contentView.setText(text);
        }
    }

    private void setText(@StringRes int text) {
        setText(getResources().getString(text));
    }

    public void setLeftRightPadding(int paddingPX) {
        binding.contentView.setCompoundDrawablePadding(paddingPX);
    }


    @SuppressLint("ResourceType")
    public void setLeftIcon(@DrawableRes int drawableId) {
        if (drawableId > 0) {
            setLeftIcon(getResources().getDrawable(drawableId));
        }
    }

    public void setLeftIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable[] drawables = binding.contentView.getCompoundDrawables();
            drawables[0] = drawable;
            setDrawables(drawables);
        }
    }

    @SuppressLint("ResourceType")
    public void setRightIcon(@DrawableRes int drawableId) {
        if (drawableId > 0) {
            setRightIcon(getResources().getDrawable(drawableId));
        }

    }

    public void setRightIcon(Drawable drawable) {
        if (drawable != null) {
            Drawable[] drawables = binding.contentView.getCompoundDrawables();
            drawables[2] = drawable;
            setDrawables(drawables);
        }
    }

    private void setDrawables(Drawable[] drawable) {
        if (drawable == null || drawable.length != 4) {
            return;
        }
        DrawableUtils.setCompoundDrawables(binding.contentView, drawable[0], drawable[1], drawable[2], drawable[3]);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bottomLineSize > 0) {
            setPaint();
            canvas.drawRect(0, getMeasuredHeight() - bottomLineSize, getMeasuredWidth(), getMeasuredHeight(), paint);
        }
    }

    public String getLiName(){
        return binding.etName.getText().toString();
    }
    @InverseBindingAdapter(attribute = "li_name",event = "li_nameAttrChanged")
    public static String getName(LineItemView itemView) {
        return itemView.getLiName();
    }

    private void setPaint() {
        if (paint == null) {
            paint = new Paint();
        }
        paint.setAntiAlias(true);
        paint.setColor(bottomLineColor);
        paint.setStyle(Paint.Style.FILL);
    }


    public static void li_nameAttrChanged(LineItemView view,
                                               final OnValueChangedListener valueChangedListener,
                                               final InverseBindingListener bindingListener) {
        if (bindingListener == null) {
            view.setListener(valueChangedListener);
        } else {
            view.setListener(() -> {
                if (valueChangedListener != null) {
                    valueChangedListener.onValueChanged();
                }
                // 通知 ViewModel
                bindingListener.onChange();
            });
        }
    }

    public void setListener(OnValueChangedListener listener) {
        this.listener = listener;
    }

    public interface OnValueChangedListener {
        void onValueChanged();
    }

}
