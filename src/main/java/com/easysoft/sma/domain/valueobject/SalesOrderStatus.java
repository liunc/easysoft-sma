package com.easysoft.sma.domain.valueobject;

public class SalesOrderStatus {

	public static final String WAITING_SCHEDULE = "1";

	public static final String SCHEDULED = "2";

	public static final String ORDERED = "3";

	public static final String PENDING_COLLECTION = "4";

	public static final String COMPLETED = "5";

	public static final String CANCELED = "6";

	public static String[] all() {
		return new String[] { WAITING_SCHEDULE, SCHEDULED, ORDERED, PENDING_COLLECTION, COMPLETED, CANCELED };
	}

}
