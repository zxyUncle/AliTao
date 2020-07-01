package com.aliTao.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aliTao.Config;
import com.aliTao.MainActivity;
import com.aliTao.R;
import com.aliTao.model.SaveUserInfo;
import com.aliTao.service.CB_NetApi;
import com.aliTao.service.JsonCallback;
import com.aliTao.utils.EventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.Call;

/**
 * Created by zxy on 2020/7/1 0001 10:43
 * ******************************************
 * * 提现申请
 * ******************************************
 */
public class WithdrawalAppFragment extends Fragment {
    public static WithdrawalAppFragment withdrawalAppFragment;
    public static ConfrimMoneyFragment confrimMoneyFragment;
    private View view;
    private RadioGroup radioGroup;
    public RadioButton userInfo, confirmMoney;

    public UserInfoFragment userInfoFragment;
    private int position;

    /**
     * Fragment 管理
     */

    public static WithdrawalAppFragment newInstance() {
        if (withdrawalAppFragment == null) {
            withdrawalAppFragment = new WithdrawalAppFragment();
        }
        return withdrawalAppFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view = inflater.inflate(R.layout.fragment_withdrawl, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        confrimMoneyFragment = ConfrimMoneyFragment.newInstance();
        userInfoFragment = UserInfoFragment.newInstance();
        radioGroup = (RadioGroup) view.findViewById(R.id.mRadioGroup);
        userInfo = (RadioButton) view.findViewById(R.id.mRadioButton1);
        confirmMoney = (RadioButton) view.findViewById(R.id.mRadioButton3);
        userInfo.setChecked(true);
        confirmMoney.setChecked(false);
        setTabSelection(0);
    }

    public void initListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.mRadioButton1) {
                    setTabSelection(0);
                } else
                    setTabSelection(1);
            }
        });
    }

    public void setTabSelection(int position) {
        //记录position
        this.position = position;
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        //更改底部导航栏按钮状态
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(fragmentTransaction);
        switch (position) {
            case 0:
                if (!userInfoFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFrameLayout, userInfoFragment, "userInfoFragment");
                }
                fragmentTransaction.show(userInfoFragment);

                break;
            case 1:
                if (!confrimMoneyFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFrameLayout, confrimMoneyFragment, "confrimMoneyFragment");
                }
                fragmentTransaction.show(confrimMoneyFragment);
                break;

        }
        fragmentTransaction.commit();
    }

    /**
     * 切换
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventbuss(Object object) {
        if (object.toString() == EventBusMessage.LAST_STEP_COMFIG) {//去用户信息
            userInfo.setChecked(true);
        } else if (object.toString() == EventBusMessage.NEXT_STEP_USERINFO) {
            confirmMoney.setChecked(true);
        }

    }


    private void hideFragments(FragmentTransaction transaction) {
        if (userInfoFragment != null)
            transaction.hide(userInfoFragment);
        if (confrimMoneyFragment != null)
            transaction.hide(confrimMoneyFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
