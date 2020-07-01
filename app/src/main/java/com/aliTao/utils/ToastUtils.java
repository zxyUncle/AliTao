package com.aliTao.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hdy on 2017/9/10.
 */

public class ToastUtils {
    private static Toast toast;
    private static Context context;

    public static void toast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
}
