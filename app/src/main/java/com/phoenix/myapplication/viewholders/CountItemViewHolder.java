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
package com.phoenix.myapplication.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.phoenix.myapplication.R;


/**
 * Created by tomas on 15/07/15.
 */
public class CountItemViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    View containerView;

    public CountItemViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.title);
        containerView = itemView.findViewById(R.id.container);
    }

    public void render(String text, int color) {
        textView.setText(text);
        containerView.setBackgroundColor(color);
    }
}
