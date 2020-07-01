package com.aliTao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aliTao.MainActivity;
import com.aliTao.R;
import com.gyf.immersionbar.ImmersionBar;

public class LoginAcitivity extends AppCompatActivity {
    EditText et_login_password;
    EditText et_login_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
        initListener();
    }

    private void initView() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .navigationBarWithKitkatEnable(true)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)//解决布局和状态栏重叠
                .statusBarColor(R.color.cffffff)
                .init();
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
    }

    private void initListener() {

    }


    /**
     * 登录按钮
     */
    public void onLogin(View view) {
        String userPhone = et_login_phone.getText().toString();
        String userPassword = et_login_password.getText().toString();
        if (userPhone.length() == 11) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "用户名不正确", Toast.LENGTH_LONG).show();
        }
    }
}
