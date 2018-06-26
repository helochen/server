package com.exchange.pool.factory.business.controller;

import org.share.msg.Message;

public interface RunnableFactory {
    /**
     * 通过命令得到业务线程
     *
     * @Param command
     * @Param data
     * */
    Runnable getRunnable(String command, Message msg);

    /**
     * 得到线程池的组
     * @param command
     * @return
     */
    String getGroup(String command);
}
