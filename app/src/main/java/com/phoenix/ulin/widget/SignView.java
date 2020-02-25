package com.phoenix.ulin.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.phoenix.ulin.R;

import java.util.ArrayList;

/**
 * 自定义签字views
 */
public class SignView extends View {
    private Paint textPaint;
    private ArrayList<Path> paths;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.black));
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(5);
        paths = new ArrayList<>();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), textPaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Path path = new Path();
                path.moveTo(x, y);
                paths.add(path);
                break;
            case MotionEvent.ACTION_MOVE:
                paths.get(paths.size() - 1).lineTo(x, y);
                invalidate();
                break;
        }
        return true;
    }

    //撤回一步
    public void withDrawoOneStep() {
        if (paths.size() > 0) {
            paths.remove(paths.size() - 1);
        }
        invalidate();
    }

    public void clearStep() {
        paths.clear();
        invalidate();
    }

    public Bitmap getBitmapFromView() {
        this.setDrawingCacheEnabled(true);  //开启图片缓存
        buildDrawingCache();  //构建图片缓存
        Bitmap bitmap = Bitmap.createBitmap(getDrawingCache());  //获取图片缓存
        setDrawingCacheEnabled(false);  //关闭图片缓存
        return bitmap;
    }

}
