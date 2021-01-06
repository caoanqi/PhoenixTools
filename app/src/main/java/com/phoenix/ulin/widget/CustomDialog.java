package com.phoenix.ulin.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoenix.ulin.R;
import com.phoenix.ulin.adapter.DialogCountSectionAdapter;
import com.phoenix.ulin.entity.DeviceBean;
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import java.util.ArrayList;
import java.util.List;

public class CustomDialog extends Dialog {

    protected CustomDialog(@NonNull Context context) {
        super(context);
    }

    private CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private View mLayout;
        private RecyclerView rvLineList;
        private Button btnSure;
        private Button btnCancel;
        Context context;

        private DialogCallback mDialogCallback;
        List<DeviceBean> deviceBeans;
        private CustomDialog mDialog;

        private List<DeviceBean> checkedDevicelist = new ArrayList<>();

        public Builder(Context context) {
            this.context = context;
            mDialog = new CustomDialog(context, R.style.CustomDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //加载布局文件
            mLayout = inflater.inflate(R.layout.dialog_custom, null, false);
            //添加布局文件到 Dialog
            mDialog.addContentView(mLayout,
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            rvLineList = mLayout.findViewById(R.id.rv_line);
            btnCancel = mLayout.findViewById(R.id.bt_cancel);
            btnSure = mLayout.findViewById(R.id.bt_sure);

        }

        private void initData() {
            DialogCountSectionAdapter adapter = new DialogCountSectionAdapter(context,
                    deviceBeans,
                    deviceBeans -> checkedDevicelist = deviceBeans);

            rvLineList.setAdapter(adapter);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);
            layoutManager.setSpanSizeLookup(lookup);
            rvLineList.setLayoutManager(layoutManager);
        }


        public Builder setSureText(String str) {
            btnSure.setText(str);
            return this;
        }

        public Builder setDeviceList(List<DeviceBean> deviceBeans) {
            this.deviceBeans = deviceBeans;
            return this;
        }

        public Builder setCancelText(String string) {
            btnCancel.setText(string);
            return this;
        }

        /**
         * 设置确定监听
         */
        public Builder setSureButton(DialogCallback listener) {
            mDialogCallback = listener;
            return this;
        }

        public CustomDialog create() {
            btnSure.setOnClickListener(view -> {
                mDialog.dismiss();
                if (mDialogCallback != null)
                    mDialogCallback.onSureClickListener(checkedDevicelist);
            });
            btnCancel.setOnClickListener(view -> {
                mDialog.dismiss();
                deviceBeans.clear();
                checkedDevicelist.clear();
            });

            mDialog.setContentView(mLayout);
            initData();

            mDialog.setCancelable(true);                //用户可以点击后退键关闭 Dialog
            mDialog.setCanceledOnTouchOutside(false);   //用户不可以点击外部来关闭 Dialog
            return mDialog;
        }
    }

    public interface DialogCallback {
        void onSureClickListener(List<DeviceBean> deviceBeans);
    }
}
