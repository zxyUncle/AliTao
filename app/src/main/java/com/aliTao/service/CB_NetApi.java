package com.aliTao.service;

import com.aliTao.model.LoginInfo;
import com.aliTao.model.UserBean;

public class CB_NetApi {
    /**
     * 登陆
     * @param json
     * @param callback
     */
    public static void login(String json,JsonCallback<LoginInfo> callback) {
        String url =UrlKit.login;
        NetApi.invokePostJson(url,json,callback);
    }

    /**
     * 保存用户信息
     */
    public static void saveUserInfo(String json,JsonCallback<LoginInfo> callback) {
        String url =UrlKit.getUrl(UrlKit.saveUserInfo);
        NetApi.invokePostJson(url,json,callback);
    }

    /**
     * 保存提现申请记录
     * @param json
     * @param callback
     */
    public static void saveWithDrawlInfo(String json,JsonCallback<LoginInfo> callback) {
        String url =UrlKit.getUrl(UrlKit.saveWithDrawInfo);
        NetApi.invokePostJson(url,json,callback);
    }

    /**
     * 保存通知的消息
     * @param json
     * @param callback
     */
    public static void saveMsgInfo (String json,JsonCallback<LoginInfo> callback) {
        String url =UrlKit.getUrl(UrlKit.saveMsgInfo);
        NetApi.invokePostJson(url,json,callback);
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

}
