package com.phoenix.ulin.widget;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.phoenix.ulin.R;

/**
 * 自定义测滑View
 */
public class SwipeRecyclerView extends RecyclerView {

    private static final String TAG = SwipeRecyclerView.class.getSimpleName();
    private int maxLength, mTouchSlop;
    private int xDown, yDown, xMove, yMove;

    private int curSelectPosition;//当前选中的item索引
    private Scroller mScroller;
    private LinearLayout mCurItemLayout, mLastItemLayout;
    private TextView mItenContent;
    private LinearLayout mLlHiden;//隐藏的布局（删除部分）
    private LinearLayout mItemDelete;
    private int mHidenwidth;
    private int mMoveWidth;
    private boolean isFirst = true;
    private Context mContext;
    private OnRightClickListener mRightListener;

    public SwipeRecyclerView(@NonNull Context context) {
        super(context);
    }

    public SwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//滑动到最小距离
        //滑动的最大距离
        maxLength = ((int) (180 * context.getResources().getDisplayMetrics().density + 0.5f));
        //初始化Scroller
        mScroller = new Scroller(context, new LinearInterpolator(context, null));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录当前按下的坐标
                xDown = x;
                yDown = y;
                //计算选中哪个Item
                int firstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                Rect itemRect = new Rect();
                final int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(itemRect);
                        if (itemRect.contains(x, y)) {
                            curSelectPosition = firstPosition + i;
                            break;
                        }
                    }
                }

                if (isFirst) {
                    //第一次，不用重置上一次的Item
                    isFirst = false;
                } else {
                    //屏幕再次接收到点击时，恢复上一次Item的状态
                    if (mLastItemLayout != null && mMoveWidth > 0) {
                        //将item右移，恢复原位
                        scrollRight(mLastItemLayout, (0 - mMoveWidth));
                        //清空变量
                        mHidenwidth = 0;
                        mMoveWidth = 0;
                    }
                }

                //取到当前选中的Item，赋给mCurItemLayout，以便对其进行左移
                View item = getChildAt(curSelectPosition - firstPosition);
                if (item != null) {
                    //获取当前选中的Item
                    MyAdapter.ViewHolder viewHolder = (MyAdapter.ViewHolder) getChildViewHolder(item);
                    mCurItemLayout = viewHolder.ll_item;
                    //找到具体元素（这与实际业务相关了~~）
                    mLlHiden = (LinearLayout) mCurItemLayout.findViewById(R.id.ll_hidden);
                    mItemDelete = (LinearLayout) mCurItemLayout.findViewById(R.id.ll_hidden);
                    mItemDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mRightListener != null) {
                                //删除
                                mRightListener.onRightClick(curSelectPosition, "");
                            }
                        }
                    });

                    //这里将删除按钮的宽度设为可以移动的距离
                    mHidenwidth = mLlHiden.getWidth();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;//为负时：手指向左滑动；为正时：手指向右滑动
                int dy = yMove - yDown;

                //左滑
                if (dx < 0 && Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop) {
                    int newScrollX = Math.abs(dx);
                    if (mMoveWidth > mHidenwidth) {
                        //超过了，不能在移动
                        newScrollX = 0;
                    } else if (mMoveWidth + newScrollX > mHidenwidth) {
                        newScrollX = mHidenwidth - mMoveWidth;
                    }
                    //左滑，每次滑动手指移动的
                    scrollLeft(mCurItemLayout, newScrollX);
                    //对移动的距离叠加
                    mMoveWidth = mMoveWidth + newScrollX;


                } else if (dx > 0) {
                    //执行有滑，这里没有做跟随，瞬间恢复
                    scrollRight(mCurItemLayout, 0 - mMoveWidth);
                    mMoveWidth = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = mCurItemLayout.getScrollX();
                if (mHidenwidth > mMoveWidth) {
                    int toX = (mHidenwidth - mMoveWidth);
                    if (scrollX > mHidenwidth / 2) {
                        scrollLeft(mCurItemLayout, toX);
                        mMoveWidth = mHidenwidth;
                    } else {
                        //不到一半时松开，则恢复原装
                        scrollRight(mCurItemLayout, 0 - mMoveWidth);
                        mMoveWidth = 0;
                    }
                }

                mLastItemLayout = mCurItemLayout;
                break;

        }

        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mCurItemLayout.scrollBy(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    private void scrollLeft(View item, int scrollX) {
        LogUtils.e("ScrollLeft:", scrollX);
        item.scrollBy(scrollX, 0);
    }

    private void scrollRight(View item, int scrollX) {
        LogUtils.e("ScrollRight:", scrollX);
        item.scrollBy(scrollX, 0);
    }

    public OnRightClickListener getmRightListener() {
        return mRightListener;
    }

    public void setmRightListener(OnRightClickListener mRightListener) {
        this.mRightListener = mRightListener;
    }

    public interface OnRightClickListener {
        void onRightClick(int position, String id);
    }

}
