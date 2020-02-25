package com.phoenix.ulin.view.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.phoenix.ulin.R;
import com.phoenix.ulin.databinding.ActivityPhotoBinding;
import com.ulin.baselib.utils.PhotoUtils;


/**
 * 图片
 */
public class PhotoActivity extends AppCompatActivity {

    private static final int TAKE_PHOTO_CODE = 0;
    private static final int CHOOSE_PHOTO_CODE = 1;
    private static final int CROP_PHOTO_CODE = 2;

    ActivityPhotoBinding activityPhotoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPhotoBinding = DataBindingUtil.setContentView(this, R.layout.activity_photo);
        initListener();
        initData();

    }

    private void initListener() {
        activityPhotoBinding.btSelectPhoto.setOnClickListener(v -> {
            chooseLocalPhoto();
        });
    }

    private void initData() {

    }

    private void takePhoto() {

    }

    /**
     * 选择本地相册图片
     */
    private void chooseLocalPhoto() {
        PhotoUtils.choosePhoto(this, CHOOSE_PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO_CODE:
                break;
            case CHOOSE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        PhotoUtils.cropPhoto(this, CROP_PHOTO_CODE, data.getData());
                    }
                }

                break;
            case CROP_PHOTO_CODE:
                if (data != null && resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        activityPhotoBinding.ivShow.setImageBitmap(bitmap);
                        boolean res = ImageUtils.save(bitmap, "/sdcard/topo/", Bitmap.CompressFormat.JPEG, true);
                        if (res) {
                            LogUtils.e("保存成功");
                        }
                    }
                }
                break;
            default:
                break;

        }

    }
}
