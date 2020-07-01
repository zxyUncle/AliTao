package com.aliTao;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.aliTao.activity.TakePhotoActivity;
import com.aliTao.adapter.AppInfosAdapter;
import com.aliTao.database.AppinfosDatabase;
import com.aliTao.fragment.MyFragment;
import com.aliTao.fragment.WithdrawalAppFragment;
import com.aliTao.model.AppInfo;
import com.aliTao.model.SaveUserInfo;
import com.aliTao.model.UserBean;
import com.aliTao.service.MonitorService;
import com.aliTao.service.NotificationCollectorService;
import com.aliTao.utils.EventBusMessage;
import com.gyf.immersionbar.ImmersionBar;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.jph.takephoto.model.TResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class MainActivity extends TakePhotoActivity {
    private Intent serviceIntent;
    public static SaveUserInfo saveUserInfo = new SaveUserInfo();

    private BottomNavigationViewEx bottomNavigationViewEx;
    private FrameLayout content;

    private Fragment myFragment;
    private Fragment withdrawFragment;
    /**
     * Fragment 管理
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        initView();
        init();
        initBottomView();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            transaction = fragmentManager.beginTransaction();
            hideFragments(transaction);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(withdrawFragment==null){
                        withdrawFragment = WithdrawalAppFragment.newInstance();
                        transaction.add(R.id.content,withdrawFragment);
                    }
                    transaction.show(withdrawFragment);
                case R.id.navigation_my:
                    if(myFragment==null){
                        myFragment = MyFragment.newInstance();
                        transaction.add(R.id.content,myFragment);
                    }
                    transaction.show(myFragment);
            }
            transaction.commit();
            return true;
        }

    };

    private void initView () {
        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomnavigation);
        content = (FrameLayout) findViewById(R.id.content);

        //状态栏和导航栏管理
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .keyboardEnable(true)
                .navigationBarWithKitkatEnable(true)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)//解决布局和状态栏重叠
                .statusBarColor(R.color.toolbar_bg)
                .init();
    }

    @Override
    public void OnTakeSuccess(TResult result) {
        EventBus.getDefault().post(new EventBusMessage(EventBusMessage.TACKPHONE_IMG_URL,result.getImage().getOriginalPath()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventbuss(Object object) {
        if (object.toString() == EventBusMessage.TACKPHONE_CAMERA) {
            takePhoto.onPickFromCapture(uri);
        } else if (object.toString() == EventBusMessage.TACKPHONE_PHONE_) {
            takePhoto.onPickFromGallery();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        startService(serviceIntent);

    }


    @SuppressLint("JavascriptInterface")
    private void init() {
//        data = getSharedPreferences("data", MODE_MULTI_PROCESS);
//        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
//        tabHost.setup();
//        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("介绍", null).setContent(R.id.tab1));
//        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("推送设置", null).setContent(R.id.tab2));
//        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("通知设置", null).setContent(R.id.tab3));
//        if (!isNotificationListenerServiceEnabled(this)) {
//            Toast.makeText(this, "请先勾选手机监听器的读取通知栏权限!", Toast.LENGTH_LONG).show();
//            return;
//        }
//        Button button = (Button) findViewById(R.id.desc_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                Uri uri = Uri.parse("https://github.com/egdw/mobile_monitor_android_simple/wiki");
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(uri);
//                startActivity(intent);
//            }
//        });
//
//        //是否监听所有的应用?
//        applicationList = (ListView) findViewById(R.id.applicationList);
//        myHandler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.what == 0x1) {
//                    applicationList.setVisibility(View.VISIBLE);
//                    adapter = new AppInfosAdapter(MainActivity.this, (List<AppInfo>) msg.obj);
//                    applicationList.setAdapter(adapter);
//                } else if (msg.what == 0x2) {
//                    applicationList.setVisibility(View.INVISIBLE);
//                } else if (msg.what == 0x3) {
//                    applicationList.setVisibility(View.VISIBLE);
//                    if (adapter == null) {
//                        Toast.makeText(MainActivity.this, "加载应用中..请耐心等待", Toast.LENGTH_LONG).show();
//                        getAppsThread();
//                    }
//                } else if (msg.what == 0x4) {
//                    //表示重启服务
//                    if (serviceIntent != null) {
//                        stopService(serviceIntent);
//                    }
////                    startService(serviceIntent);
//                }
//
//            }
//        };
//        if (data.getBoolean("listenAll", true) == false) {
//            Toast.makeText(MainActivity.this, "加载应用中..请耐心等待", Toast.LENGTH_LONG).show();
//            getAppsThread();
//        }
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        //底部导航栏设置监听
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //状态栏字体是否显示为黑se
        Config.setStatusBarMode(this,true);
        checkNotification();
      //  addFragment(MyFragment.class.getSimpleName());
        bottomNavigationViewEx.setSelectedItemId(R.id.navigation_my);
        serviceIntent = new Intent(MainActivity.this, MonitorService.class);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(serviceIntent);
    }


    @Override
    protected void onPause() {
        //当界面返回到桌面之后.清除通知设置当中的数据.减少内存占有
//        if (applicationList != null) {
//            applicationList.setAdapter(null);
//            adapter = null;
//        }
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!isNotificationListenerServiceEnabled(this)) {
            openNotificationAccess();
            toggleNotificationListenerService();
            Toast.makeText(this, "请先勾选手机监听器的读取通知栏权限!", Toast.LENGTH_LONG).show();
            return;
        }
//        if (applicationList != null && applicationList.getVisibility() == View.VISIBLE && applicationList.getAdapter() == null) {
//            //说明需要重新获取数据
//            myHandler.sendEmptyMessage(0x3);
//        }
    }

    public Handler myHandler = null;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void toggleNotificationListenerService() {
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(new ComponentName(this, NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }

    /**
     * 检查通知权限
     */
    private void checkNotification() {
        boolean enabled = NotificationManagerCompat.from(this).areNotificationsEnabled();
        if (enabled) {
            return;
        }
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("您当前还未允许通知，请开启")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whiNch) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                            Intent intent = new Intent();
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                            intent.putExtra("app_package", MainActivity.this.getPackageName());
                            intent.putExtra("app_uid", MainActivity.this.getApplicationInfo().uid);
                            MainActivity.this.startActivity(intent);
                        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                            MainActivity.this.startActivity(intent);
                        } else {
                            Intent localIntent = new Intent();
                            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                            MainActivity.this.startActivity(localIntent);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    private static boolean isNotificationListenerServiceEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }

    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private void openNotificationAccess() {
        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    public List<AppInfo> getAppInfos() {
        PackageManager pm = getApplication().getPackageManager();
        List<PackageInfo> packgeInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
        /* 获取应用程序的名称，不是包名，而是清单文件中的labelname
            String str_name = packageInfo.applicationInfo.loadLabel(pm).toString();
			appInfo.setAppName(str_name);
    	 */
        for (PackageInfo packgeInfo : packgeInfos) {
            String appName = packgeInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packgeInfo.packageName;
            Drawable drawable = packgeInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName, drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    public void getAppsThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("开始...", "");
                List<AppInfo> appInfos = getAppInfos();
                //获取当前所有的应用信息.
                //从数据库读取从前同意的应用信息
                SQLiteDatabase database = AppinfosDatabase.getReadInstance(MainActivity.this);
                HashMap<String, Object> infos = AppinfosDatabase.getInstance(MainActivity.this).selectAll(database);

                if (infos != null && infos.size() > 0) {
                    for (int i = 0; i < appInfos.size(); i++) {
                        AppInfo next = appInfos.get(i);
                        String packageName = next.getPackageName();
                        if (infos.get(packageName) != null) {
                            //如果相同的话
                            next.setOpen(true);
                            appInfos.set(i, next);
                        }
                    }
                }
                Message message = new Message();
                message.what = 0x1;
                message.obj = appInfos;
                myHandler.sendMessage(message);
            }
        }).start();
    }



    /**
     * 添加指定Fragment到界面中
     *
     * @param id
     */
    void addFragment(String id) {
        Fragment mBaseFragment = getFragmentByID(id);
        if (mBaseFragment != null) {
            addFragment(mBaseFragment);
        } else {
            Log.e("wqx","addFragment ,mBaseFragment is null ! ");
        }
    }

    /**
     * 替换指定ID的Fragment
     *
     * @param fragment
     */
    void addFragment(Fragment fragment) {

        transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.content, fragment);
        }
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);//
        // 设置动画效果
        try {
            transaction.commitAllowingStateLoss();// 提交 该方法允许状态丢失
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    Fragment getFragmentByID(String id) {
        if (id == null && id.equals("")) {
            Log.e("getFragmentByID ", id =" + id");
            return null;
        }
        Fragment mFragment = null;
        if (id.equals(WithdrawalAppFragment.class.getSimpleName())) {
            if (withdrawFragment == null) {
                mFragment = WithdrawalAppFragment.newInstance();
            } else {
                mFragment = withdrawFragment;
            }

        }

        if (id.equals(MyFragment.class.getSimpleName())) {
            if (myFragment == null) {
                mFragment = MyFragment.newInstance();
            } else {
                mFragment = myFragment;
            }

        }

        return mFragment;
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (withdrawFragment != null) {
            transaction.hide(withdrawFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    private void initBottomView() {
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableAnimation(false);
    }
}
