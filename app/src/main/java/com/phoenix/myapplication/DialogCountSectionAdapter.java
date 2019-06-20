/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phoenix.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.phoenix.myapplication.viewholders.CountCheckBoxItemViewHolder;
import com.phoenix.myapplication.viewholders.CountFooterViewHolder;
import com.phoenix.myapplication.viewholders.CountHeaderViewHolder;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DialogCountSectionAdapter extends SectionedRecyclerViewAdapter<CountHeaderViewHolder,
        CountCheckBoxItemViewHolder,
        CountFooterViewHolder> {

    protected Context context = null;
    List<DeviceBean> deviceBeans;
    List<DeviceBean> deviceCheckedList = new ArrayList<>();
    List<DeviceBean.DeviceInfo> devInfoList = new ArrayList<>();

    Map<Integer, Map<String, List<DeviceBean.DeviceInfo>>> intCheckedMap = new HashMap<>();
    Map<String, List<DeviceBean.DeviceInfo>> strCheckedMap = new HashMap<>();
    DeviceBean devBean;
    CheckBoxItemCallback callback;

    public DialogCountSectionAdapter(Context context, List<DeviceBean> deviceBeans, CheckBoxItemCallback callback) {
        this.context = context;
        this.deviceBeans = deviceBeans;
        this.callback = callback;
    }

    @Override
    protected int getItemCountForSection(int section) {
        return deviceBeans.get(section).getDeviceInfos().size();
    }

    @Override
    protected int getSectionCount() {
        return deviceBeans == null ? 0 : deviceBeans.size();
    }

    public DialogCountSectionAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return true;
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    protected CountHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_header, parent, false);
        return new CountHeaderViewHolder(view);
    }

    @Override
    protected CountFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_footer, parent, false);
        return new CountFooterViewHolder(view);
    }

    @Override
    protected CountCheckBoxItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_check_box_item, parent, false);
        return new CountCheckBoxItemViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(CountHeaderViewHolder holder, int section) {
        holder.render("Section " + (section));
    }

    @Override
    protected void onBindSectionFooterViewHolder(CountFooterViewHolder holder, int section) {
        holder.render("Footer " + (section));
    }

    @Override
    protected void onBindItemViewHolder(CountCheckBoxItemViewHolder holder, int section, int position) {
        try {
            holder.render(deviceBeans.get(section).getDeviceTypeName(),
                    deviceBeans.get(section).getDeviceInfos().get(position).getDeviceName(),
                    (isChecked, imgName, deviceType) -> {
                        checkedDevices(isChecked, section, position, deviceType, deviceBeans.get(section).getDeviceInfos().get(position));
                        if (callback != null) {
                            //callback.onCheckBoxListener(deviceCheckedList);
                            Log.e("Map Data:", JSON.toJSON(intCheckedMap).toString());
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理选择的设备
     *
     * @param isChecked  是否选中
     * @param section    大类别 索引
     * @param position   小类索引
     * @param deviceType 设备类型
     * @param deviceInfo 设备信息
     */
    private void checkedDevices(boolean isChecked, int section, int position,
                                String deviceType, DeviceBean.DeviceInfo deviceInfo) {
        try {
            if (isChecked) {
                if (intCheckedMap.size() > 0 && (intCheckedMap.get(section) != null && intCheckedMap.get(section).size() > 0)) {
                    if (intCheckedMap.get(section).get(deviceType).size() > 0) {
                        intCheckedMap.get(section).get(deviceType).add(deviceInfo);
                    }
                    //deviceCheckedList.get(section).getDeviceInfos().add(deviceInfo);
                } else {
                    devBean = new DeviceBean();
                    devInfoList.add(deviceInfo);
                    devBean.setDeviceInfos(devInfoList);
                    strCheckedMap.put(deviceType, devInfoList);
                    intCheckedMap.put(section, strCheckedMap);
                }
            } else {
                DeviceBean devBeanDelete = deviceCheckedList.get(section);
                List<DeviceBean.DeviceInfo> devInfoListDelete = devBeanDelete.getDeviceInfos();
                devInfoListDelete.remove(position);
                if (devInfoListDelete.size() <= 0) {
                    deviceCheckedList.remove(section);
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 选择返回处理回掉
     */
    public interface CheckBoxItemCallback {
        void onCheckBoxListener(List<DeviceBean> deviceBeans);
    }
}
