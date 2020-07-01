package com.aliTao.adapter;

import android.text.Html;

import com.aliTao.R;
import com.aliTao.model.UserBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Collections;
import java.util.List;

public class UserDataAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public UserDataAdapter (List<UserBean> data) {
        super(R.layout.adapter_user, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tvWithDrawMoney,item.getAmount() == null ? "提现金额：00" :
                Html.fromHtml(
                        "<b><font color=#333333>提现金额：</b><font><b><big><font color=#F96F49>"+item.getAmount()+"</b></big><font/>"));

        helper.setText(R.id.tvTime,item.getApplyTime() == null ?"申请时间：--": "申请时间："+item.getApplyTime());

        helper.addOnClickListener(R.id.tverification);
    }
}
