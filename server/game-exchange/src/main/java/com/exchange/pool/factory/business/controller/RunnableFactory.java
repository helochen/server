package com.exchange.pool.factory.business.controller;

import org.share.msg.Message;

import java.util.concurrent.Callable;

public interface RunnableFactory {
    /**
     * 通过命令得到业务线程
     *
     * @Param command
     * @Param data
     * */
    Callable getRunnable(Message msg);

    /**
     * 得到线程池的组
     * @param command
     * @return
     */
    String getGroup(String command);
}
