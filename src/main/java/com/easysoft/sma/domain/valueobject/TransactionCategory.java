package com.easysoft.sma.domain.valueobject;

import com.easysoft.lib.common.exception.BadRequestException;

public class TransactionCategory {

	public static final String RECHARGE = "1";

	public static final String CONSUME = "2";

	public static final String REFUND = "3";

	public static String[] all() {
		return new String[] { RECHARGE, CONSUME, REFUND };
	}

	public static void check(String value) {

		String[] all = all();
		for (int i = 0; i < all.length; i++) {

			if (all[i].equals(value)) {
				return;
			}
		}

		throw new BadRequestException("Unsupported category of transaction, '" + value + "'.");
	}
}