package com.aliTao.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aliTao.R;

import java.util.Timer;
import java.util.TimerTask;

public class VerificationDialog extends Dialog {

    /**
     * 上下文对象 *
     */
    Activity context;

    private AppCompatTextView btn_save, btn_cancle_pop;

    public EditText text_code;



    public VerificationDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public VerificationDialog(Activity context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_verification_code);
        text_code = (EditText) findViewById(R.id.text_code);
        btn_save = (AppCompatTextView) findViewById(R.id.btn_save_pop);
        btn_cancle_pop = (AppCompatTextView) findViewById(R.id.btn_cancle_pop);
        showkeyBourd();
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text_code.getText().toString().trim().length()>=6){
                    Toast.makeText(context, "验证码失效", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancle_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hindKeyBourd();
                dismiss();
            }
        });

        this.setCancelable(false);
    }

    void hindKeyBourd() {
        InputMethodManager manager = (InputMethodManager) text_code.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(text_code.getWindowToken(), 0);
    }

    void showkeyBourd() {
        //自动获取焦点
        text_code.setFocusable(true);
        text_code.setFocusableInTouchMode(true);
        text_code.requestFocus();
        //200毫秒后弹出软键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) text_code.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(text_code, 0);
            }
        }, 200);

    }
}
