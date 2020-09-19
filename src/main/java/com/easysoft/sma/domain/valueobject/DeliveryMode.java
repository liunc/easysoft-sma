package com.easysoft.sma.domain.valueobject;

public class DeliveryMode {

    public static final String EXPRESS = "1";

    public static final String HOME = "2";

    public static final String SELF = "3";

    public static String[] all() {
        
        return new String[] { EXPRESS, HOME, SELF };
    }

    public static String get(String value) {

        String[] all = all();
        for (int i = 0; i < all.length; i++) {
            if (all[i].equals(value)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unsupported mode of delivery, '" + value + "'.");
    }

    public static void check(String values) {

        String[] arr = values.split(",");
        String[] all = all();
        for (int i = 0; i < arr.length; i++) {
            boolean success = false;
            for (int j = 0; j < all.length; j++) {
                if (arr[i].equals(all[j])) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                throw new IllegalArgumentException("Unsupported mode of delivery, '" + arr[i] + "'.");
            }
        }
    }
}