package com.aliTao.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aliTao.Config;
import com.aliTao.MainActivity;
import com.aliTao.R;
import com.aliTao.model.BaseResult;
import com.aliTao.model.SaveUserInfo;
import com.aliTao.service.CB_NetApi;
import com.aliTao.service.JsonCallback;
import com.aliTao.utils.EventBusMessage;
import com.aliTao.utils.ToastUtils;
import com.aliTao.view.MakeLoansDialog;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.Call;

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
    private TextView tvName,tvPhone,tvIdentityCard,tvBankCard,tvBankName,tvDealPassword;
    MakeLoansDialog makeLoansDialog;
    private void initView(View view) {
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        tvIdentityCard = (TextView) view.findViewById(R.id.tvIdentityCard);
        tvBankCard = (TextView) view.findViewById(R.id.tvBankCard);
        tvBankName = (TextView) view.findViewById(R.id.tvBankName);
        tvDealPassword = (TextView) view.findViewById(R.id.tvDealPassword);

        SaveUserInfo saveUserInfo = MainActivity.saveUserInfo;
        tvName.setText(saveUserInfo.getRealName());
        tvPhone.setText(saveUserInfo.getTel());
        tvIdentityCard.setText(saveUserInfo.getIdCard());
        tvBankCard.setText(saveUserInfo.getBankCard());
        tvBankName.setText(saveUserInfo.getBankName());
        tvDealPassword.setText(saveUserInfo.getPwd());
    }
    public static ConfrimMoneyFragment newInstance() {
        if (confrimMoneyFragment == null) {
            confrimMoneyFragment = new ConfrimMoneyFragment();
        }
        return confrimMoneyFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventbuss(Object object) {
        if (object.toString() == EventBusMessage.UPDATEBANKNAME) {
            tvBankName.setText(MainActivity.saveUserInfo.getBankName());
        }

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
        initView(view);
        initListener();
    }

    private void initListener() {
        btnComfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeLoansDialog = new MakeLoansDialog(getActivity(), R.style.Translucent_NoTitle, new MakeLoansDialog.onConfirmListener() {
                    @Override
                    public void onClick(double money) {
                        if (UserInfoFragment.lable == 1) {
                            saveUserInfo(new Gson().toJson(MainActivity.saveUserInfo));
                        }
                        WithDrawlMoney w = new WithDrawlMoney();
                        w.setUserName(Config.GetString(getActivity(),Config.SHARE_USER_NAME));
                        w.setAmount(money+"");
                        saveWithDrawl(new Gson().toJson(w));

                    }
                });
                makeLoansDialog.show();
            }
        });
        btnLastStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(EventBusMessage.LAST_STEP_COMFIG);
            }
        });
    }

    class WithDrawlMoney {
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        private String userName;
        private String amount;
    }

    public  void saveWithDrawl(String json) {
        final MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.showLoginHUD();
        CB_NetApi.saveWithDrawlInfo(json, new JsonCallback<BaseResult>() {
            @Override
            public void onFail(Call call, Exception e, int id) {
                mainActivity.goneLoginHUD();
            }

            @Override
            public void onException(final BaseResult response, int id) {
                mainActivity.goneLoginHUD();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            ToastUtils.toast(getContext(),response.getInfo() == null ?"申请失败" : response.getInfo());
                        }
                    }
                });
            }

            @Override
            public void onSuccess(final BaseResult response, int id) {
                mainActivity.goneLoginHUD();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            makeLoansDialog.dismiss();
                            ToastUtils.toast(getContext(),"申请成功");
                        }
                    }
                });
            }
        });
    }
    /**
     * 保存用户信息
     * @param json
     */
    public void saveUserInfo (String json) {
        CB_NetApi.saveUserInfo(json, new JsonCallback<BaseResult>() {
            @Override
            public void onFail(Call call, Exception e, int id) {

            }

            @Override
            public void onException(BaseResult response, int id) {

            }

            @Override
            public void onSuccess(BaseResult response, int id) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

}
