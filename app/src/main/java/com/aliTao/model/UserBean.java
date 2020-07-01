package com.aliTao.model;

import java.util.List;

public class UserBean {

    /**
     * code : 0
     * msg : 成功
     * data : [{"id":1277902124712505300,"userName":"321612916","amount":"1000","applyTime":"2020-06-30 17:49:34"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1277902124712505300
         * userName : 321612916
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
}
