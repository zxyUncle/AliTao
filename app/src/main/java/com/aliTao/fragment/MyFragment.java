package com.aliTao.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.aliTao.Config;
import com.aliTao.R;
import com.aliTao.adapter.UserDataAdapter;
import com.aliTao.model.UserBean;
import com.aliTao.service.CB_NetApi;
import com.aliTao.service.JsonCallback;
import com.aliTao.utils.ToastUtils;
import com.aliTao.view.VerificationDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

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
    private SmartRefreshLayout mSmartRefresh;

    private UserDataAdapter adapter;
    private TextView tvNoMessage;

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
        mSmartRefresh = (SmartRefreshLayout) view.findViewById(R.id.mSmartRefresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        toolbar = (AppCompatTextView) view.findViewById(R.id.toolbar);
        tvNoMessage = (TextView) view.findViewById(R.id.tvNoMessage);
        Config.setStatusBarMode(getActivity(), true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (adapter == null) {
            adapter = new UserDataAdapter(new ArrayList<UserBean.DataBean>());
        }
        recyclerView.setAdapter(adapter);
        initGetUserInfo();
//        List<UserBean.DataBean> userBeanList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            UserBean.DataBean userBean = new UserBean.DataBean();
//            userBean.setAmount(i + "00");
//            userBean.setApplyTime("2020-06-30 12:00:00");
//            userBeanList.add(userBean);
//        }
//        adapter.setNewData(userBeanList);
    }

    private void initGetUserInfo () {
        CB_NetApi.getUserInfo(Config.GetString(getContext(), Config.SHARE_USER_NAME), new JsonCallback<UserBean>() {
            @Override
            public void onFail(Call call, Exception e, int id) {

            }

            @Override
            public void onException(UserBean response, int id) {

            }

            @Override
            public void onSuccess(final UserBean response, int id) {
                mSmartRefresh.finishRefresh();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null && response.getData() != null && response.getData().size() !=0) {
                            adapter.setNewData(response.getData());
                            tvNoMessage.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            tvNoMessage.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
    private void initLinstener() {
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initGetUserInfo();
             //   mSmartRefresh.finishRefresh();
            }
        });
        mSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSmartRefresh.finishLoadMoreWithNoMoreData();
            }
        });
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
