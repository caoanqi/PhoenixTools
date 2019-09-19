package com.phoenix.myapplication.view.expand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.phoenix.myapplication.R;
import com.phoenix.myapplication.databinding.ActivityExpandListViewBinding;

public class ExpandListViewActivity extends AppCompatActivity {

    ActivityExpandListViewBinding activityExpandListViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityExpandListViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_expand_list_view);
        initData();
        initListener();
    }

    private void initData() {
        MyExtendableListViewAdapter adapter = new MyExtendableListViewAdapter();
        activityExpandListViewBinding.elvTest.setAdapter(adapter);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {

        activityExpandListViewBinding.elvTest.setAdapter(new MyExtendableListViewAdapter());
        //设置分组的监听
        activityExpandListViewBinding.elvTest.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            Toast.makeText(getApplicationContext(), groupString[groupPosition], Toast.LENGTH_SHORT).show();
            LogUtils.e("lin");

            return false;
        });


        //设置子项布局监听
//        activityExpandListViewBinding.elvTest.setOnChildClickListener((parent, v,
//                                                                            groupPosition, childPosition, id) -> {
//            Toast.makeText(getApplicationContext(), childString[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
//            LogUtils.e("you");
//
//            return false;
//
//        });
        //控制他只能打开一个组
        activityExpandListViewBinding.elvTest.setOnGroupExpandListener(groupPosition -> {
            LogUtils.e("cao");
            int count = new MyExtendableListViewAdapter().getGroupCount();
            for (int i = 0; i < count; i++) {
                if (i != groupPosition) {
                    activityExpandListViewBinding.elvTest.collapseGroup(i);
                }
            }
        });

    }

    public String[] groupString = {"射手", "辅助", "坦克", "法师"};
    public String[][] childString = {
            {"孙尚香", "后羿", "马可波罗", "狄仁杰"},
            {"孙膑", "蔡文姬", "鬼谷子", "杨玉环"},
            {"张飞", "廉颇", "牛魔", "项羽"},
            {"诸葛亮", "王昭君", "安琪拉", "干将"}

    };

    class MyExtendableListViewAdapter extends BaseExpandableListAdapter {
        private Context mcontext;
        private boolean isBold=true;
        public void setBold(boolean isBold){
            this.isBold=isBold;
        }

        @Override
        // 获取分组的个数
        public int getGroupCount() {
            return groupString.length;
        }

        //获取指定分组中的子选项的个数
        @Override
        public int getChildrenCount(int groupPosition) {
            return childString[groupPosition].length;
        }

        //        获取指定的分组数据
        @Override
        public Object getGroup(int groupPosition) {
            return groupString[groupPosition];
        }

        //获取指定分组中的指定子选项数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childString[groupPosition][childPosition];
        }

        //获取指定分组的ID, 这个ID必须是唯一的
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //获取子选项的ID, 这个ID必须是唯一的
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //分组和子选项是否持有稳定的ID, 就是说底层数据的改变会不会影响到它们
        @Override
        public boolean hasStableIds() {
            return true;
        }

        /**
         * 获取显示指定组的视图对象
         *
         * @param groupPosition 组位置
         * @param isExpanded    该组是展开状态还是伸缩状态
         * @param convertView   重用已有的视图对象
         * @param parent        返回的视图对象始终依附于的视图组
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.tvTitle = convertView.findViewById(R.id.tv_parent_name);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
            groupViewHolder.tvTitle.setText(groupString[groupPosition]);
            if (isBold){
                groupViewHolder.tvTitle.setTypeface(Typeface.DEFAULT_BOLD);
            }
            return convertView;
        }

        /**
         * 获取一个视图对象，显示指定组中的指定子元素数据。
         * 取得显示给定分组给定子位置的数据用的视图
         *
         * @param groupPosition 组位置
         * @param childPosition 子元素位置
         * @param isLastChild   子元素是否处于组中的最后一个
         * @param convertView   重用已有的视图(View)对象
         * @param parent        返回的视图(View)对象始终依附于的视图组
         * @return
         * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
         * android.view.ViewGroup)
         */

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.tvTitle = convertView.findViewById(R.id.tv_child_name);
                childViewHolder.iv_topo_logo = convertView.findViewById(R.id.iv_topo_logo);
                convertView.setTag(childViewHolder);

            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.tvTitle.setText(childString[groupPosition][childPosition]);

            childViewHolder.iv_topo_logo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            LogUtils.e("hello1");
                            break;
                        case MotionEvent.ACTION_MOVE:
                            LogUtils.e("hello2");
                            break;

                        case MotionEvent.ACTION_UP:
                            LogUtils.e("hello3");
                            break;

                        default:break;
                    }
                    return false;
                }
            });
            return convertView;
        }

        //指定位置上的子元素是否可选中
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class GroupViewHolder {
            TextView tvTitle;
        }

        class ChildViewHolder {
            TextView tvTitle;
            ImageView iv_topo_logo;
        }
    }

}
