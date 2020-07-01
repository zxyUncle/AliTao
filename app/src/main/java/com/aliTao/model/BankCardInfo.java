package com.aliTao.model;

import java.util.List;

public class BankCardInfo {
    /**
     * bank : ABC
     * cardType : DC
     * key : 6228430120000000000
     * messages : []
     * stat : ok
     * validated : true
     */

    private String bank;
    private String cardType;
    private String key;
    private String stat;
    private boolean validated;
    private List<?> messages;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public List<?> getMessages() {
        return messages;
    }

    public void setMessages(List<?> messages) {
        this.messages = messages;
    }
}
