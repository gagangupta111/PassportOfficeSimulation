package com.simulation.constants;

import java.util.HashMap;
import java.util.Map;

public enum Status {

    INITIAL_INTRODUCTION_QUEUE      (0, "IN_QUEUE_INITIAL_INTRODUCTION"),
    INITIAL_INTRODUCTION_PROGRESS   (1, "IN_PROGRESS_INITIAL_INTRODUCTION"),
    DOCUMENTS_VERIFICATION_QUEUE    (2, "IN_QUEUE_DOCUMENTS_VERIFICATION"),
    DOCUMENTS_VERIFICATION_PROGRESS (3, "IN_PROGRESS_DOCUMENTS_VERIFICATION"),
    POLICE_VERIFICATION_QUEUE       (4, "IN_QUEUE_POLICE_VERIFICATION"),
    POLICE_VERIFICATION_PROGRESS    (5, "IN_PROGRESS_POLICE_VERIFICATION"),
    BIO_METRIC_VERIFICATION_QUEUE   (6, "IN_QUEUE_BIO_METRIC_VERIFICATION"),
    BIO_METRIC_VERIFICATION_PROGRESS(7, "IN_PROGRESS_BIO_METRIC_VERIFICATION"),
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
