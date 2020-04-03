package com.phoenix.ulin.view.photo;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ImageUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.databinding.ActivityCircleImageViewBinding;

/**
 * 圆形图片
 */
public class CircleImageViewActivity extends PreferenceActivity {

    ActivityCircleImageViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_circle_image_view);

        binding.civPic.setImageBitmap(ImageUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.day)));

        binding.btnTab.setOnClickListener(v -> {
            binding.civPic.setImageResource(0);
        });

        binding.button.setOnClickListener(v -> {
            binding.civPic.setImageResource(R.mipmap.ic_launcher);
        });

//        List<String> list = new ArrayList<String>();
//        list.add("22");
//        //warn
//        List<String> test = (ArrayList<String>) list.subList(0, 1);
//
//        List<String> list2 = new ArrayList<String>(list.subList(0, 1));



    }
}
