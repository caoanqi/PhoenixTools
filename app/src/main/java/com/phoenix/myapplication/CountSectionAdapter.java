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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phoenix.myapplication.viewholders.CountFooterViewHolder;
import com.phoenix.myapplication.viewholders.CountHeaderViewHolder;
import com.phoenix.myapplication.viewholders.CountItemViewHolder;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;


public class CountSectionAdapter extends SectionedRecyclerViewAdapter<CountHeaderViewHolder,
        CountItemViewHolder,
        CountFooterViewHolder> {

    protected Context context = null;
    List<DeviceBean> deviceBeans;

    public CountSectionAdapter(Context context) {
        this.context = context;
    }

    public CountSectionAdapter(Context context, List<DeviceBean> deviceBeans) {
        this.context = context;
        this.deviceBeans = deviceBeans;
    }

    @Override
    protected int getItemCountForSection(int section) {
        return deviceBeans.get(section).getDeviceInfos().size();
    }

    @Override
    protected int getSectionCount() {
        return deviceBeans == null ? 0 : deviceBeans.size();
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
    protected CountItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.view_count_item, parent, false);
        return new CountItemViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(CountHeaderViewHolder holder, int section) {
        holder.render(deviceBeans.get(section).getDeviceTypeName());
    }

    @Override
    protected void onBindSectionFooterViewHolder(CountFooterViewHolder holder, int section) {
        holder.render("Footer " + (section));
    }

    protected int[] colors = new int[]{0xfff44336, 0xff2196f3, 0xff009688, 0xff8bc34a, 0xffff9800};

    @Override
    protected void onBindItemViewHolder(CountItemViewHolder holder, int section, int position) {
        try {
            holder.render(deviceBeans.get(section).getDeviceInfos().get(position).getDeviceName(), colors[section]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
