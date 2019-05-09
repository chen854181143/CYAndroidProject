package com.chenyang.androidproject.bean.base;

import java.io.Serializable;

/**
 * bean类的公共实体
 */
public class BaseBean implements Serializable {
    private String resultcode;
    private String reason;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
