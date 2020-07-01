package com.aliTao.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aliTao.Config;
import com.aliTao.MainActivity;
import com.aliTao.R;
import com.aliTao.model.BankCardInfo;
import com.aliTao.model.SaveUserInfo;
import com.aliTao.model.UploadImageBean;
import com.aliTao.service.CB_NetApi;
import com.aliTao.service.JsonCallback;
import com.aliTao.utils.EventBusMessage;
import com.aliTao.utils.ToastUtils;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import okhttp3.Call;

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
    private EditText etName, etPhone, etBankCard, etDealPassword, etIdNum;
    private boolean isImgBack = true;
    private String faceImg;
    private String backImg;


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

        etName = (EditText) view.findViewById(R.id.etName);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        etBankCard = (EditText) view.findViewById(R.id.etBankCard);
        etDealPassword = (EditText) view.findViewById(R.id.etDealPassword);
        etIdNum = (EditText) view.findViewById(R.id.etIdNum);

    }

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
        initListener();
        return view;
    }

    private void next() {
        if (etName.getText().toString().trim().length() == 0) {
            ToastUtils.toast(getActivity(), "姓名不正确");
            return;
        }
        if (etPhone.getText().toString().trim().length() != 11) {
            ToastUtils.toast(getActivity(), "手机号不正确");
            return;
        }
        if (etIdNum.getText().toString().trim().length() == 0) {
            ToastUtils.toast(getActivity(), "身份证号码不正确");
            return;
        }
        if (etBankCard.getText().toString().trim().length() == 0) {
            ToastUtils.toast(getActivity(), "银行卡不正确");
            return;
        }

        if (etDealPassword.getText().toString().trim().length() == 0) {
            ToastUtils.toast(getActivity(), "密码不正确");
            return;
        }

        if (faceImg == null) {
            ToastUtils.toast(getActivity(), "请上传身份证正面");
            return;
        }
        if (backImg == null) {
            ToastUtils.toast(getActivity(), "请上传身份证反面");
            return;
        }
        verificationBank(etBankCard.getText().toString().trim());//获取银行卡信息
        uploadFaceImg(faceImg); //上传身份证正面

        SaveUserInfo saveUserInfo = MainActivity.saveUserInfo;
        saveUserInfo.setRealName(etName.getText().toString().trim());
        saveUserInfo.setUserName(Config.GetString(getActivity(), Config.SHARE_USER_NAME));
        saveUserInfo.setIdCard(etIdNum.getText().toString().trim());
        saveUserInfo.setTel(etPhone.getText().toString().trim());
        saveUserInfo.setBankCard(etBankCard.getText().toString().trim());
        saveUserInfo.setPwd(etDealPassword.getText().toString().trim());

        EventBus.getDefault().post(EventBusMessage.NEXT_STEP_USERINFO);
    }

    /**
     * 获取银行卡信息
     * @param bankNo
     */
    private void verificationBank(String bankNo) {
        CB_NetApi.verificationBankInfo(bankNo, new JsonCallback<BankCardInfo>() {
            @Override
            public void onFail(Call call, Exception e, int id) {

            }

            @Override
            public void onException(BankCardInfo response, int id) {

                Log.e("wqx","error");
            }

            @Override
            public void onSuccess(BankCardInfo response, int id) {
                if (response != null) {
                    //获取银行信息
                    StringBuilder stringBuilder = new StringBuilder();
                    try {
                        //获取assets资源管理器
                        AssetManager assetManager = getActivity().getAssets();
                        //通过管理器打开文件并读取
                        BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("bankcode.json")));
                        String line;
                        while ((line = bf.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String result = stringBuilder.toString();
                    Map map = JSON.parseObject(result);
                    MainActivity.saveUserInfo.setBankName((String) map.get(response.getBank()));
                    EventBus.getDefault().post(EventBusMessage.UPDATEBANKNAME);
                }
            }
        });
    }

    /**
     * 上传身份证正面
     */
    private void uploadFaceImg(String url) {
        CB_NetApi.uploadImage(url, new JsonCallback<UploadImageBean>() {
            @Override
            public void onFail(Call call, Exception e, int id) {

            }

            @Override
            public void onException(UploadImageBean response, int id) {

            }

            @Override
            public void onSuccess(UploadImageBean response, int id) {
                if (response != null) {
                    uploadReverseImg(backImg);
                    MainActivity.saveUserInfo.setFontCard(response.getData().getPath() == null ?"" :response.getData().getPath());
                }
            }
        });
    }
    /**
     * 上传身份证反面
     */
    private void uploadReverseImg(String url) {
        CB_NetApi.uploadImage(url, new JsonCallback<UploadImageBean>() {
            @Override
            public void onFail(Call call, Exception e, int id) {

            }

            @Override
            public void onException(UploadImageBean response, int id) {

            }

            @Override
            public void onSuccess(UploadImageBean response, int id) {
                if (response != null) {
                    MainActivity.saveUserInfo.setReverseCard(response.getData().getPath() == null ?"" :response.getData().getPath());
                }
            }
        });
    }
    private void initListener() {
        btnBankCardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        mImgFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImgBack = false;
                mLiMask.setVisibility(View.VISIBLE);
            }
        });
        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImgBack = true;
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
            if (isImgBack) {
                backImg = eventBusMessage.getData().toString();
                Glide.with(getActivity()).load(file).into(mImgBack);
                mImageView1.setVisibility(View.INVISIBLE);
                mTvBack.setVisibility(View.INVISIBLE);
            } else {
                faceImg = eventBusMessage.getData().toString();
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
