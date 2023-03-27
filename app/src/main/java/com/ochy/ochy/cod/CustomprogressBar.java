package com.ochy.ochy.cod;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.ochy.ochy.R;

public class CustomprogressBar extends Dialog {

    public CustomprogressBar(@NonNull Context context) {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        setTitle(null);
        setCancelable(true);
        setOnCancelListener(null);
        View v = LayoutInflater.from(context).inflate(R.layout.progrssbar_layout, null);
        setContentView(v);
    }
}
