package com.simulation.constants;

import java.util.HashMap;
import java.util.Map;

public enum Status {

    INITIAL_INTRODUCTION_QUEUE      (0, "IN QUEUE... INITIAL_INTRODUCTION"),
    INITIAL_INTRODUCTION_PROGRESS   (1, "IN PROGRESS... INITIAL_INTRODUCTION"),
    DOCUMENTS_VERIFICATION_QUEUE    (2, "IN QUEUE... DOCUMENTS_VERIFICATION"),
    DOCUMENTS_VERIFICATION_PROGRESS (3, "IN PROGRESS... DOCUMENTS_VERIFICATION"),
    POLICE_VERIFICATION_QUEUE       (4, "IN QUEUE... POLICE_VERIFICATION"),
    POLICE_VERIFICATION_PROGRESS    (5, "IN PROGRESS... POLICE_VERIFICATION"),
    BIO_METRIC_VERIFICATION_QUEUE   (6, "IN QUEUE... BIO_METRIC_VERIFICATION"),
    BIO_METRIC_VERIFICATION_PROGRESS(7, "IN PROGRESS... BIO_METRIC_VERIFICATION"),
    PROCESSED                       (8, "PROCESSED");

    public static Map<Integer, Status> map = new HashMap<Integer, Status>();

    static {

        map.put(0, INITIAL_INTRODUCTION_QUEUE);
        map.put(1, INITIAL_INTRODUCTION_PROGRESS);
        map.put(2, DOCUMENTS_VERIFICATION_QUEUE);
        map.put(3, DOCUMENTS_VERIFICATION_PROGRESS);
        map.put(4, POLICE_VERIFICATION_QUEUE);
        map.put(5, POLICE_VERIFICATION_PROGRESS);
        map.put(6, BIO_METRIC_VERIFICATION_QUEUE);
        map.put(7, BIO_METRIC_VERIFICATION_PROGRESS);
        map.put(8, PROCESSED);

    }

    private final int levelCode;
    private final String message;

    Status(int levelCode, String message) {
        this.levelCode = levelCode;
        this.message = message;
    }

    public int getLevelCode() {
        return this.levelCode;
    }

    public String getMessage() {
        return message;
    }
}
