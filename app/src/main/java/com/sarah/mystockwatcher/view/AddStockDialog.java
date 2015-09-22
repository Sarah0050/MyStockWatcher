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
public class AddStockDialog extends AlertDialog implements View.OnClickListener {

    private EditText etAdd;
    private TextView tvOk;
    private TextView tvCancel;

    private DialogCallback dialogCallback;

    protected AddStockDialog(Context context, DialogCallback dialogCallback) {
        super(context);
        this.dialogCallback = dialogCallback;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_stock, null);


        etAdd = (EditText) view.findViewById(R.id.add_dialog_input);
        tvOk = (TextView) view.findViewById(R.id.add_dialog_ok);
        tvCancel = (TextView) view.findViewById(R.id.add_dialog_cancel);

        setView(view);

        tvOk.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }


//    @Override
//    public void onStart() {
//        super.onStart();
//
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
