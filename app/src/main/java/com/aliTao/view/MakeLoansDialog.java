package com.aliTao.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.aliTao.MainActivity;
import com.aliTao.R;
import com.aliTao.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zxy on 2020/7/1 0001 11:21
 * ******************************************
 * * 出款弹出框
 * ******************************************
 */
public class MakeLoansDialog extends Dialog {

    /**
     * 上下文对象 *
     */
    Activity context;

    private AppCompatTextView btn_save, btn_cancle_pop,tvBankName;

    public EditText text_code;

    public onConfirmListener listener;

    public MakeLoansDialog(Activity context) {
        super(context);
        this.context = context;
    }

    public MakeLoansDialog(Activity context, int theme,onConfirmListener confirmListener) {
        super(context, theme);
        this.context = context;
        this.listener = confirmListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_make_loans);
        text_code = (EditText) findViewById(R.id.text_code);
        btn_save = (AppCompatTextView) findViewById(R.id.btn_save_pop);
        btn_cancle_pop = (AppCompatTextView) findViewById(R.id.btn_cancle_pop);
        text_code.setInputType(InputType.TYPE_CLASS_NUMBER);
        tvBankName = (AppCompatTextView) findViewById(R.id.dialog_tvBAnkName);
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
                if (text_code.getText().toString().trim().length() == 0) {
                    ToastUtils.toast(context,"请输入提现金额");
                    return;
                }
                if (Integer.parseInt(text_code.getText().toString()) > 0) {
                    listener.onClick(Double.parseDouble(text_code.getText().toString()));
                } else {
                    ToastUtils.toast(context,"请输入正确的提现金额");
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

        initChangeEdit();
        if (MainActivity.saveUserInfo != null && MainActivity.saveUserInfo.getBankCard().length()>5) {
            String cardNum = MainActivity.saveUserInfo.getBankCard().substring(MainActivity.saveUserInfo.getBankCard().length()-4,MainActivity.saveUserInfo.getBankCard().length());
            //银行名称
            tvBankName.setText(MainActivity.saveUserInfo.getBankName()+"("+cardNum+")");
        }

        this.setCancelable(false);
    }

    /**
     * 监听输入
     */
    void initChangeEdit() {
        text_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString().trim();
                if (text.length()>0) {
                    btn_save.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.edit_bg_make_loans));
                    btn_save.setTextColor(context.getResources().getColor(R.color.cffffff));
                } else {
                    btn_save.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.edit_bg_grary_make_loans));
                    btn_save.setTextColor(context.getResources().getColor(R.color.c666666));
                }
            }
        });
    }

    void hindKeyBourd() {
        InputMethodManager manager = (InputMethodManager) text_code.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(text_code.getWindowToken(), 0);
    }


    public interface onConfirmListener{
        void onClick(double money);
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
