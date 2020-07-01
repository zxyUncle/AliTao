package com.aliTao.model;

public class SingleUserInfo {

    /**
     * code : 0
     * msg : 鎴愬姛
     * data : {"id":1277926769583022000,"userName":"321612916","ipAddr":"127.0.0.1","realName":"张三","tel":"171512121","bankCard":"6665156","bankName":"建设","pwd":"1251223","idCard":"545615615","fontCard":"http://img.1zblog.com/defaultHead.jpg","reverseCard":"http://img.1zblog.com/defaultHead.jpg","faceVideo":"http://img.1zblog.com/defaultHead.jpg","createdTime":"2020-06-30 19:28:11"}
     */

    private int code;
    private String msg;
    private SaveUserInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public SaveUserInfo getData() {
        return data;
    }

    public void setData(SaveUserInfo data) {
        this.data = data;
    }


}
