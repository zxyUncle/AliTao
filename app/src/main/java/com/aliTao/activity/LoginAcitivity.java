package com.aliTao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aliTao.Config;
import com.aliTao.MainActivity;
import com.aliTao.R;
import com.aliTao.model.BaseResult;
import com.aliTao.service.CB_NetApi;
import com.aliTao.service.JsonCallback;
import com.aliTao.utils.ToastUtils;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import okhttp3.Call;

public class LoginAcitivity extends BaseActivity {
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
        et_login_password.setText(Config.GetString(this,Config.SHARE_PASSWORD));
        et_login_phone.setText(Config.GetString(this,Config.SHARE_USER_NAME));
    }

    private void initListener() {

    }


    /**
     * 登录按钮
     */
    public void onLogin(View view) {
        String userPhone = et_login_phone.getText().toString();
        String userPassword = et_login_password.getText().toString();
        if (TextUtils.isEmpty(userPhone)) {
            ToastUtils.toast(this,"请输入用户名");
            return ;
        }
        if (TextUtils.isEmpty(userPassword)) {
            ToastUtils.toast(this, "请输入密码");
            return;
        }
        if (userPhone.length() == 11) {
            Login login = new Login();
            login.setPwd(userPassword);
            login.setTel(userPhone);
            login(new Gson().toJson(login));
            Config.SaveString(LoginAcitivity.this,Config.SHARE_USER_NAME,userPhone);
            Config.SaveString(LoginAcitivity.this,Config.SHARE_PASSWORD,userPassword);
        } else {
            Toast.makeText(this, "用户名不正确", Toast.LENGTH_LONG).show();
        }
    }

    public void login(String json){
        showLoginHUD();
        CB_NetApi.login(json, new JsonCallback<BaseResult>() {
            @Override
            public void onFail(Call call, Exception e, int id) {
                goneLoginHUD();
                Log.e("wqx",e.toString());
            }

            @Override
            public void onException(final BaseResult response, int id) {
                goneLoginHUD();
                Log.e("wqx",response.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {

                            ToastUtils.toast(LoginAcitivity.this,response.getInfo() == null ?"失败":response.getInfo());
                        } else {
                            ToastUtils.toast(LoginAcitivity.this,"失败");

                        }
                    }
                });
            }

            @Override
            public void onSuccess(BaseResult response, int id) {
                goneLoginHUD();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setClass(LoginAcitivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    class Login{
        private String tel;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        private String pwd;
    }
}
