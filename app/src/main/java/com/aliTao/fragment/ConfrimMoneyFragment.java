package com.aliTao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aliTao.R;
import com.aliTao.utils.EventBusMessage;
import com.aliTao.view.MakeLoansDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zxy on 2020/7/1 0001 10:44
 * ******************************************
 * * 确认出款
 * ******************************************
 */
public class ConfrimMoneyFragment extends Fragment {
    public static ConfrimMoneyFragment confrimMoneyFragment;
    private View view;
    private Button btnComfig, btnLastStep;

    public static ConfrimMoneyFragment newInstance() {
        if (confrimMoneyFragment == null) {
            confrimMoneyFragment = new ConfrimMoneyFragment();
        }
        return confrimMoneyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confrim_money, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnComfig = (Button) view.findViewById(R.id.btnComfig);
        btnLastStep = (Button) view.findViewById(R.id.btnLastStep);
        initView();
    }

    private void initView() {
        btnComfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MakeLoansDialog(getActivity(), R.style.Translucent_NoTitle).show();
            }
        });
        btnLastStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(EventBusMessage.LAST_STEP_COMFIG);
            }
        });

    }
}
