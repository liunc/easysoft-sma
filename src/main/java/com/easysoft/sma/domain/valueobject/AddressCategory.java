package com.easysoft.sma.domain.valueobject;

public class AddressCategory {

	public static final String SENDER = "1";

	public static final String CONSIGNEE = "2";

	public static String[] all() {

		return new String[] { SENDER, CONSIGNEE };
	}

	public static String get(String value) {

		if (!SENDER.equals(value) && !CONSIGNEE.equals(value)) {
			throw new IllegalArgumentException("Unsupported mode of delivery, '" + value + "'.");
		}
		return value;
	}
}