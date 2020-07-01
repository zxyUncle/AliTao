package com.aliTao;

import android.app.Activity;
import android.view.View;

public class Config {
    /**
     * 状态栏字体
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
