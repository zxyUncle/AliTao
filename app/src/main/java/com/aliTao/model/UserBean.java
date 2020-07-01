package com.aliTao.model;

public class UserBean {

    /**
     * id : 1277902124712505300
     * userName : 321612926
     * amount : 1000
     * applyTime : 2020-06-30 17:49:34
     */

    private long id;
    private String userName;
    private String amount;
    private String applyTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
