package com.aliTao.service;

public class UrlKit {
    private static final String API_BASE_URL = "http://115.28.208.167:5001/monitor/";
    public static String getUrl(String action) {
        return new StringBuilder(API_BASE_URL).append(API_BASE_URL).append(action).toString();
    }


    //登陆
    public static final String login = "http://www.alt69.cn/index/user/do_login.html";
    //文件上传
    public static final String fileUpload = "file/upload";
    //保存用户信息
    public static final String saveUserInfo = "user/save";
    //提现申请信息
    public static final String saveWithDrawInfo= "expressive/save";
    //保存获取的短信信息
    public  static final String saveMsgInfo = "notice/save";
    //获取用户提现信息
    public static final String getUserInfo = "expressive/{userName}";
    //查询银行卡信息
    public static final String queryBankInfo = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json";

}
