package com.easysoft.sma.domain.valueobject;

public class TransactionSource {

    public static final String WECHAT = "1";

    public static final String ALIPAY = "2";

    public static final String CASH = "3";

    public static final String ACCOUNT = "4";

    public static String[] all() {
        return new String[] { WECHAT, ALIPAY, CASH, ACCOUNT };
    }

    public static String[] recharge() {
        return new String[] { WECHAT, ALIPAY, CASH };
    }
}