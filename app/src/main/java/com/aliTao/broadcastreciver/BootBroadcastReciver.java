package com.aliTao.broadcastreciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aliTao.service.MonitorService;


/**
 * Created by hdy on 2017/9/10.
 */

public class BootBroadcastReciver extends BroadcastReceiver {
    private MonitorService monitorService;
    private boolean flag;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("zxy","onReceive");
        Intent serviceIntent = new Intent(context, MonitorService.class);
        context.startService(serviceIntent);
    }
}
