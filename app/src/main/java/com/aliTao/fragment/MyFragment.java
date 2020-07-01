package com.aliTao.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.aliTao.Config;
import com.aliTao.R;
import com.aliTao.adapter.UserDataAdapter;
import com.aliTao.model.UserBean;
import com.aliTao.utils.ToastUtils;
import com.aliTao.view.VerificationDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxy on 2020/7/1 0001 10:57
 * ******************************************
 * * 我的
 * ******************************************
 */
public class MyFragment extends Fragment {

    public static MyFragment myFragment;
    public View view;
    public RecyclerView recyclerView;
    public AppCompatTextView toolbar;

    private UserDataAdapter adapter;

    public static MyFragment newInstance() {
        if (myFragment == null) {
            myFragment = new MyFragment();
        }
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, null);
        initView();
        initLinstener();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        toolbar = (AppCompatTextView) view.findViewById(R.id.toolbar);
        Config.setStatusBarMode(getActivity(), true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null) {
            adapter = new UserDataAdapter(new ArrayList<UserBean>());
        }
        recyclerView.setAdapter(adapter);
        List<UserBean> userBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserBean userBean = new UserBean();
            userBean.setAmount(i + "00");
            userBean.setApplyTime("2020-06-30 12:00:00");
            userBeanList.add(userBean);
        }
        adapter.setNewData(userBeanList);
    }

    private void initLinstener() {
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                if (view.getId() == R.id.tverification) {
                    new VerificationDialog(getActivity(), R.style.Translucent_NoTitle).show();
                }
            }
        });
    }
}
