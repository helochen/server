package com.service.controller;

public interface RunnableFactory {

    Runnable getRunnable(String command, Object data);

    String getGroup(String command);
}
