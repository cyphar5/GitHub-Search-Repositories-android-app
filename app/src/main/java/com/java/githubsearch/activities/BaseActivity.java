package com.java.githubsearch.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.java.githubsearch.R;

public class BaseActivity extends AppCompatActivity {

    Dialog dialogTransparent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        View view = LayoutInflater.from(this).inflate(
                R.layout.circular_bg, null);

        dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(
                R.color.black_80);
        dialogTransparent.setContentView(view);
    }


    public void showDialog(){

        if(dialogTransparent != null && !dialogTransparent.isShowing()){
            dialogTransparent.show();
        }

    }

    public void hideDialog() {

        if (dialogTransparent != null && dialogTransparent.isShowing()) {
            dialogTransparent.dismiss();
        }
    }
}
