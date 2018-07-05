package com.simulation.constants;

import java.util.HashMap;
import java.util.Map;

public class Status {

    public static Map<Integer, Status> map = new HashMap<Integer, Status>();

    static {

        map.put(0, new Status(0, "IN_QUEUE_INITIAL_INTRODUCTION"));
        map.put(1, new Status(1, "IN_PROGRESS_INITIAL_INTRODUCTION"));

    }

    private final int levelCode;
    private final String message;

    public Status(int levelCode, String message) {
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
