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

import com.aliTao.R;
import com.aliTao.utils.EventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (userInfoFragment == null && fragment instanceof UserInfoFragment) {
            userInfoFragment = (UserInfoFragment) fragment;
        } else if (confrimMoneyFragment == null && fragment instanceof ConfrimMoneyFragment) {
            confrimMoneyFragment = (ConfrimMoneyFragment) fragment;
        }
    }

    /**
     * Fragment 管理
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

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
        radioGroup = (RadioGroup) view.findViewById(R.id.mRadioGroup);
        userInfo = (RadioButton) view.findViewById(R.id.mRadioButton1);
        confirmMoney = (RadioButton) view.findViewById(R.id.mRadioButton3);
        if (fragmentManager == null) {
            fragmentManager = getChildFragmentManager();
        }
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
        //更改底部导航栏按钮状态
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
//        transaction.hide(mHomeFragment).hide(mMessageFragment).hide(mMineFragment).hide(mMoreFragment).commit();
        transaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (userInfoFragment == null) {
                    userInfoFragment = UserInfoFragment.newInstance();
                    transaction.add(R.id.mFrameLayout, userInfoFragment, "userInfoFragment");
                } else {
                    transaction.show(userInfoFragment);
                }
                break;
            case 1:
                if (confrimMoneyFragment == null) {
                    confrimMoneyFragment = ConfrimMoneyFragment.newInstance();
                    transaction.add(R.id.mFrameLayout, confrimMoneyFragment, "confrimMoneyFragment");
                } else {
                    transaction.show(confrimMoneyFragment);
                }
                break;

        }
        transaction.commit();
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

        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
