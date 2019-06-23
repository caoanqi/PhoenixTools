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
package com.phoenix.myapplication.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.phoenix.myapplication.R;


/**
 * Created by tomas on 15/07/15.
 */
public class CountCheckBoxItemViewHolder extends RecyclerView.ViewHolder {

    CheckBox cbLine;
    ImageView ivLineKind;

    public CountCheckBoxItemViewHolder(View itemView) {
        super(itemView);
        cbLine = itemView.findViewById(R.id.cb_line);
        ivLineKind = itemView.findViewById(R.id.iv_line_kind);
    }

    public void render(String deviceType, String imgName, CheckBoxCallback callback) {
        ivLineKind.setBackgroundColor(R.mipmap.ic_launcher);
        if (callback != null) {
            cbLine.setOnCheckedChangeListener(null);//清掉监听器
            cbLine.setChecked(false);
            cbLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("CheckBox Status:",String.valueOf(isChecked));
                    if (isChecked) {
                        callback.onCheckboxItemClick(true, imgName, deviceType);
                    } else {
                        callback.onCheckboxItemClick(false, imgName, deviceType);
                    }
                }
            });
        }
    }

    public interface CheckBoxCallback {
        void onCheckboxItemClick(boolean isChecked, String imgName, String deviceType);
    }
}
