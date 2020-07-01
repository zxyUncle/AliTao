package com.aliTao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

public class BaseActivity extends AppCompatActivity {
    KProgressHUD hud;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoginHUD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hud = KProgressHUD.create(BaseActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("加载中...")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
            }
        });
    }

    public void goneLoginHUD() { runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if (hud != null) {
                hud.dismiss();
            }
        }
    });
    }

}
