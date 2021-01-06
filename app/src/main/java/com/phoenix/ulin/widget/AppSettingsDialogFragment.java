package com.phoenix.ulin.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

public class AppSettingsDialogFragment extends DialogFragment {

    public static final String TAG = "AppSettingsDialogFragment";
    public static final int DEFAULT_SETTINGS_REQ_CODE = 16061;

    private static final String ARG_POSITIVE_BUTTON = "positiveButton";
    private static final String ARG_NEGATIVE_BUTTON = "negativeButton";
    private static final String ARG_RATIONALE_TITLE = "rationaleTitle";
    private static final String ARG_RATIONALE_MESSAGE = "rationaleMsg";
    private static final String ARG_REQUEST_CODE = "requestCode";

    private int positiveButton;
    private int negativeButton;
    private String rationaleTitle;
    private String rationaleMsg;
    private int requestCode;

    public AppSettingsDialogFragment() {

    }

    public static AppSettingsDialogFragment newInstance(
            @StringRes int positiveButton, @StringRes int negativeButton,
            @NonNull String rationaleTitle, @NonNull String rationaleMsg,
            int requestCode) {

        AppSettingsDialogFragment fragment = new AppSettingsDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITIVE_BUTTON, positiveButton);
        args.putInt(ARG_NEGATIVE_BUTTON, negativeButton);
        args.putString(ARG_RATIONALE_TITLE, rationaleTitle);
        args.putString(ARG_RATIONALE_MESSAGE, rationaleMsg);
        args.putInt(ARG_REQUEST_CODE, requestCode);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (getArguments() != null) {
            positiveButton = getArguments().getInt(ARG_POSITIVE_BUTTON);
            negativeButton = getArguments().getInt(ARG_NEGATIVE_BUTTON);
            rationaleTitle = getArguments().getString(ARG_RATIONALE_TITLE);
            rationaleMsg = getArguments().getString(ARG_RATIONALE_MESSAGE);
            requestCode = getArguments().getInt(ARG_REQUEST_CODE);
        }

        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false)
                .setTitle(rationaleTitle)
                .setMessage(rationaleMsg)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getContext() instanceof Activity) {
                            Activity activity = (Activity) getContext();
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            activity.startActivityForResult(intent, requestCode);
                        }
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

}