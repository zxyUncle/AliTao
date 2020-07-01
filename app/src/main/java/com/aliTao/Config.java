package com.aliTao;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class Config {

    public static final String SHARE_USER_NAME = "userName";
    public static final String SHARE_PASSWORD = "password";

    /**
     * 存入一个string
     *
     * @param context
     * @param key
     * @param value
     */
    public static void SaveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE); //私有数据

        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString(key, value);

        editor.commit();//提交修改
    }

    /**
     * 获取一个string
     * @param context
     * @param key
     * @return
     */
    public static String GetString(Context context, String key) {
        SharedPreferences share = context.getSharedPreferences(key, context.MODE_PRIVATE);
        return share.getString(key, "");
    }

    /**
     * 状态栏字体
     *
     * @param activity
     * @param bDark
     */
    public static void setStatusBarMode(Activity activity, boolean bDark) {
        //6.0以上
        View decorView = activity.getWindow().getDecorView();
        if (decorView != null) {
            int vis = decorView.getSystemUiVisibility();
            if (bDark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }
}
