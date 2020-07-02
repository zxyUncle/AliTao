package com.aliTao.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import com.aliTao.R;

import java.util.Set;

/**
 * Created by hdy on 2017/9/9.
 * 用于监听事件
 */

public class MonitorService extends Service {
    private String SCKEY;
    private SharedPreferences data;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * 初始化操作
     */
    public void init() {
        if (!isNotificationListenerServiceEnabled(this)) {
            openNotificationAccess();
        }
        toggleNotificationListenerService();
        startForeground(this);
    }


    @Override
    public void onDestroy() {
        //进行自动重启
        Intent intent = new Intent(MonitorService.this, MonitorService.class);
        //重新开启服务
        startService(intent);
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        uploadAll();
        return super.onStartCommand(intent, flags, startId);
    }

    public static void startForeground(Service context) {
        //设置常驻通知栏
        //保持为前台应用状态
        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("AliTao后台运行中...")
                .setContentText("")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_MIN)
                .setSmallIcon(R.mipmap.icon_app)
                .setAutoCancel(true);
        Notification notification = builder.build();
        context.startForeground(8888, notification);
    }


    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this,NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }

    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    private static boolean isNotificationListenerServiceEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }
}
