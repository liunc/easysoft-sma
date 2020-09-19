package com.easysoft.sma.domain.valueobject;

public class TransactionCategory {

	public static final String RECHARGE = "1";

	public static final String CONSUME = "2";

	public static final String REFUND = "3";

	public static String[] all() {
		return new String[] { RECHARGE, CONSUME, REFUND };
	}
}