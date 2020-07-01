package com.aliTao.utils;

public class EventBusMessage {
    public static String LAST_STEP_COMFIG="确认出款上一步";
    public static String NEXT_STEP_USERINFO="用户信息下一步";
    public static String TACKPHONE_PHONE_="获取照相机";
    public static String TACKPHONE_CAMERA="获取相册";
    public static String TACKPHONE_IMG_URL="获取相册";
    public static String UPDATEBANKNAME="更新银行名称";


    public EventBusMessage(String tag) {
        this.tag = tag;
    }

    public EventBusMessage(String tag, Object data) {
        this.tag = tag;
        this.data = data;
    }

    private String tag;
    private Object data;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
