package com.test.webatch.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class KeyContext implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer contextId;

    private byte[] keyList;


    private Date setupDate;


    public Integer getContextId() {
        return contextId;
    }


    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }


    public byte[] getKeyList() {
        return keyList;
    }

    public void setKeyList(byte[] keyList) {
        this.keyList = keyList;
    }


    public Date getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("contextId", contextId);
        map.put("keyList", keyList);
        map.put("setupDate", setupDate);
        return map;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        "contextId="+contextId+
        "keyList="+keyList+
        "setupDate="+setupDate+
        "]";
    }

    public void fillDefaultValues() {
        if (keyList == null) keyList = null;
        if (setupDate == null) setupDate = new Date();
    }
}