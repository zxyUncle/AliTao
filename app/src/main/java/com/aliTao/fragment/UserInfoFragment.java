package com.aliTao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aliTao.R;

public class UserInfoFragment extends Fragment {
    public static UserInfoFragment userInfoFragment;
    private View view;
    private Button btnBankCardNext;
    public static WithdrawalAppFragment withdrawalAppFragment;
    public static UserInfoFragment newInstance(WithdrawalAppFragment fragment) {
        if (userInfoFragment  == null) {
            userInfoFragment = new UserInfoFragment();
        }
        withdrawalAppFragment = fragment;
        return userInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info,null);
        initView();
        return view;
    }

    private void initView() {
        btnBankCardNext = (Button) view.findViewById(R.id.btnBankCardNext);
        btnBankCardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                withdrawalAppFragment.confirmMoney.setChecked(true);
            }
        });
    }
}
