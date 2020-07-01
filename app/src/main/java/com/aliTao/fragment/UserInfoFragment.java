package com.aliTao.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliTao.R;
import com.aliTao.utils.EventBusMessage;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by zxy on 2020/7/1 0001 10:43
 * ******************************************
 * * 用户信息
 * ******************************************
 */
public class UserInfoFragment extends Fragment {
    public static UserInfoFragment userInfoFragment;
    private View view;
    private LinearLayout mLiMask, mLLCamera, mLLPhone;
    private Button btnBankCardNext;
    private ImageView mImgFront, mImgBack;
    private ImageView mImageView, mImageView1;
    private TextView mTvBack, mTvFront;
    private boolean isImgBack = true ;

    public static UserInfoFragment newInstance() {
        if (userInfoFragment == null) {
            userInfoFragment = new UserInfoFragment();
        }
        return userInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_info, null);
        initView();
        return view;
    }

    private void initView() {
        mLiMask = (LinearLayout) view.findViewById(R.id.mLiMask);
        mLLCamera = (LinearLayout) view.findViewById(R.id.mLLCamera);
        mLLPhone = (LinearLayout) view.findViewById(R.id.mLLPhone);
        btnBankCardNext = (Button) view.findViewById(R.id.btnBankCardNext);
        mImgFront = (ImageView) view.findViewById(R.id.mImgFront);
        mImgBack = (ImageView) view.findViewById(R.id.mImgBack);
        mImageView = (ImageView) view.findViewById(R.id.mImageView);
        mImageView1 = (ImageView) view.findViewById(R.id.mImageView1);
        mTvBack = (TextView) view.findViewById(R.id.mTvBack);
        mTvFront = (TextView) view.findViewById(R.id.mTvFront);
        btnBankCardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(EventBusMessage.NEXT_STEP_USERINFO);
            }
        });

        mImgFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImgBack =false;
                mLiMask.setVisibility(View.VISIBLE);
            }
        });
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImgBack =true;
                mLiMask.setVisibility(View.VISIBLE);
            }
        });
        mLiMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLiMask.setVisibility(View.GONE);
            }
        });
        mLLCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLiMask.setVisibility(View.GONE);
                EventBus.getDefault().post(EventBusMessage.TACKPHONE_CAMERA);
            }
        });
        mLLPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLiMask.setVisibility(View.GONE);
                EventBus.getDefault().post(EventBusMessage.TACKPHONE_PHONE_);
            }
        });
    }

//    private void Photo(){
//        ProgressDialog show = ProgressDialog.show(getActivity(), "Title", "Message");
//    }

    /**
     * 获取图片
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventbuss(EventBusMessage eventBusMessage) {
        if (eventBusMessage.getTag() == EventBusMessage.TACKPHONE_IMG_URL) {//去用户信息
            File file = new File(eventBusMessage.getData().toString());
            if(isImgBack){
                Glide.with(getActivity()).load(file).into(mImgBack);
                mImageView1.setVisibility(View.INVISIBLE);
                mTvBack.setVisibility(View.INVISIBLE);
            }else{
                Glide.with(getActivity()).load(file).into(mImgFront);
                mImageView.setVisibility(View.INVISIBLE);
                mTvFront.setVisibility(View.INVISIBLE);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
