package com.sarah.mystockwatcher.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sarah.mystockwatcher.R;


/**
 * Created by sarah.sun on 2015/9/18.
 */
public class EditSettingDialog extends AlertDialog implements View.OnClickListener {

    private EditText etAdd;
    private TextView tvOk;
    private TextView tvCancel;

    private DialogCallback dialogCallback;

    protected EditSettingDialog(Context context, DialogCallback dialogCallback) {
        super(context);
        this.dialogCallback = dialogCallback;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_setting_expected_rate, null);

        etAdd = (EditText) view.findViewById(R.id.setting_dialog_expected_rate);
        tvOk = (TextView) view.findViewById(R.id.setting_dialog_btn_ok);
        tvCancel = (TextView) view.findViewById(R.id.setting_dialog_btn_cancel);

        setView(view);

        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_setting_expected_rate);
//
//        etAdd = (EditText) findViewById(R.id.setting_dialog_expected_rate);
//        tvOk = (TextView) findViewById(R.id.setting_dialog_btn_ok);
//        tvCancel = (TextView) findViewById(R.id.setting_dialog_btn_cancel);
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        tvOk.setOnClickListener(this);
//        tvCancel.setOnClickListener(this);
//    }

    @Override
    public void onClick(View v) {
        if (v == tvOk) {
            dialogCallback.clickOk(etAdd.getText().toString());
            dismiss();
        }

        if (v == tvCancel) {
            dismiss();
        }
    }
}
