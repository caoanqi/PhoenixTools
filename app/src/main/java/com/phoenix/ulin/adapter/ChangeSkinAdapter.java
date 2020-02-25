package com.phoenix.ulin.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.entity.ChangeSkinBean;

import java.util.List;

/**
 * 换肤适配器
 */
public class ChangeSkinAdapter extends RecyclerView.Adapter<ChangeSkinAdapter.ChangeSkinViewHolder> {

    private List<ChangeSkinBean> changeSkinBeans;
    private Context context;
    private SkinCallback callback;
    private int lastPos = 0;//上一次的选中项

    public ChangeSkinAdapter(Context context, List<ChangeSkinBean> changeSkinBeans, SkinCallback callback) {
        this.changeSkinBeans = changeSkinBeans;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ChangeSkinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_change_skin, viewGroup, false);
        return new ChangeSkinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeSkinViewHolder changeSkinViewHolder, int i) {
        if (changeSkinViewHolder != null) {
            String themeName = SPUtils.getInstance().getString("theme", "dayTheme");
            if (themeName.equals(changeSkinBeans.get(i).getName())) {
                changeSkinViewHolder.ivChecked.setImageResource(R.drawable.ic_check_box_black_24dp);
                lastPos = i;
            } else {
                changeSkinViewHolder.ivChecked.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
            }
            if ("dayTheme".equals(themeName)) {
                changeSkinViewHolder.ivShow.setImageResource(R.drawable.day);
            } else {
                changeSkinViewHolder.ivShow.setImageResource(R.drawable.night);
            }

            changeSkinViewHolder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (callback != null) {
                                callback.onChooseSkin(changeSkinBeans.get(i).getName(), changeSkinBeans.get(i).getSkinColor(), i,lastPos);
                            }
                        }
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return changeSkinBeans.size();
    }

    public class ChangeSkinViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivShow;
        private ImageView ivChecked;

        public ChangeSkinViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShow = itemView.findViewById(R.id.iv_skin);
            ivChecked = itemView.findViewById(R.id.iv_skin_check);
        }
    }

    public interface SkinCallback {
        void onChooseSkin(String name, int color, int pos,int lastPos);
    }
}
