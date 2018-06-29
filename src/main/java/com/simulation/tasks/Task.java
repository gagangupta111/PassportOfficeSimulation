package com.simulation.tasks;

import com.simulation.workers.Worker;

public interface Task {

    public static final String INITIAL_TASK = "INITIAL_TASK";
    public static final String DOC_VER_TASK = "DOC_VER_TASK";
    public static final String POLICE_VER_TASK = "POLICE_VER_TASK";
    public static final String BIO_VER_TASK = "BIO_VER_TASK";

    public void execute(Worker worker);

}