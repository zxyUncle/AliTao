package com.aliTao.service;

import com.aliTao.model.BankCardInfo;
import com.aliTao.model.BaseResult;
import com.aliTao.model.SaveUserInfo;
import com.aliTao.model.SingleUserInfo;
import com.aliTao.model.UploadImageBean;
import com.aliTao.model.UserBean;

import java.io.File;

public class CB_NetApi extends NetApi{
    /**
     * 登陆
     * @param json
     * @param callback
     */
    public static void login(String json,JsonCallback<BaseResult> callback) {
        String url =UrlKit.login;
        invokePostJson(url,json,callback);
    }

    /**
     * 保存用户信息
     */
    public static void saveUserInfo(String json,JsonCallback<BaseResult> callback) {
        String url =UrlKit.getUrl(UrlKit.saveUserInfo);
        invokePostJson(url,json,callback);
    }

    /**
     * 保存提现申请记录
     * @param json
     * @param callback
     */
    public static void saveWithDrawlInfo(String json,JsonCallback<BaseResult> callback) {
        String url =UrlKit.getUrl(UrlKit.saveWithDrawInfo);
        invokePostJson(url,json,callback);
    }

    /**
     * 保存通知的消息
     * @param json
     * @param callback
     */
    public static void saveMsgInfo (String json,JsonCallback<BaseResult> callback) {
        String url =UrlKit.getUrl(UrlKit.saveMsgInfo);
        invokePostJson(url,json,callback);
    }

    /**
     * 获取指定用户的提现记录
     * @param userName
     * @param callback
     */
    public static void getUserInfo (String userName, JsonCallback<UserBean> callback) {
        String url = UrlKit.getUrl(UrlKit.getUserInfo.replace("userName",userName));
        NetApi.invokeGet(url,null,callback);
    }
    /**
     * 获取指定用户信息
     * @param userName
     * @param callback
     */
    public static void getSingleUserInfo (String userName, JsonCallback<SingleUserInfo> callback) {
        String url = UrlKit.getUrl(UrlKit.getSingleUserInfo.replace("userName",userName));
        NetApi.invokeGet(url,null,callback);
    }
    /**
     * 查询银行卡信息
     * @param cardNum
     * @param callback
     */
    public static void verificationBankInfo (String cardNum, JsonCallback<BankCardInfo> callback) {
        String url = UrlKit.queryBankInfo;
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.add("cardBinCheck", true);
        requestParameters.add("_input_charset", "utf-8");
        requestParameters.add("cardNo", cardNum);
        invokeGet(url,requestParameters.getMapParameters(),callback);
    }

    /**
     * 文件上传
     * @param file
     * @param callback
     */
    public static  void uploadImage(String file,JsonCallback<UploadImageBean> callback) {
        String url = UrlKit.getUrl(UrlKit.fileUpload);
        RequestParameters requestParameters = new RequestParameters();
        requestParameters.addFile("file",file);
        invokePostUpload(url,null,requestParameters.getMapFileParameters(),callback);
    }
}
