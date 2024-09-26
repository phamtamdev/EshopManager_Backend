package com.tambao.EshopManeger_Backend.Utils;

import java.util.concurrent.atomic.AtomicLong;

public class OrderCodeGenerator {
    private static final String PREFIX = "AB";
    private static final int RANDOM_BOUND = 10000;
    private static final AtomicLong COUNTER = new AtomicLong();

    public static String generateOrderCode() {
        long timestamp = System.currentTimeMillis();
        int randomValue = (int) (Math.random() * RANDOM_BOUND);
        long counterValue = COUNTER.getAndIncrement();
        String orderCode = String.format("%s%010d", PREFIX, timestamp % 10000000000L + randomValue + counterValue);
        return orderCode;
    }
}
