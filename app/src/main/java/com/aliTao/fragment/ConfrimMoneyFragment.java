package com.aliTao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliTao.R;

/**
 * 确认出款
 */
public class ConfrimMoneyFragment extends Fragment {
    public static ConfrimMoneyFragment confrimMoneyFragment;
    private View view;
    public static ConfrimMoneyFragment newInstance() {
        if (confrimMoneyFragment  == null) {
            confrimMoneyFragment = new ConfrimMoneyFragment();
        }
        return confrimMoneyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confrim_money,null);
        initView();
        return view;
    }

    private void initView() {

    }
}
