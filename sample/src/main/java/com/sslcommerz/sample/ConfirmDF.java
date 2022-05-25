package com.sslwireless.sslsdkintegration;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by Screenshot on 1/4/17.
 */

public class ConfirmDF extends DialogFragment {

    Context mContext;
    boolean isCancellable;
    String type = "", strExitTitle = "";
    int uiType = 0;

    public ConfirmDF() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //point = getArguments().getInt("point");
        if (getArguments().containsKey("isCancellable")) {
            isCancellable = getArguments().getBoolean("isCancellable");
        }
        if (getArguments().containsKey("Type")) {
            type = getArguments().getString("Type");
        }
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_common, container, false);
        //binding = DialogBkashSaveSslcBinding.inflate(inflater, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mContext = getContext();

        LottieAnimationView iconImage = view.findViewById(R.id.iconImage);
        TextView headerText = view.findViewById(R.id.headerText);
        TextView descText = view.findViewById(R.id.descText);

        headerText.setText("Success");
        descText.setText(type);

        iconImage.setAnimation("new_success_lottie.json");

        view.findViewById(R.id.doneButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            if (isCancellable) {
                dialog.setCancelable(true);
            } else {
                dialog.setCancelable(false);
            }

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //final View decorView = getDialog().getWindow().getDecorView();
        }
    }
}
