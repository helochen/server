package com.exchange.pool.factory.io.controller.action;


import org.share.msg.IOResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stage.io.IOAction;


/**
 * 响应函数
 */
public class IOCallBackAction {

    private static final Logger logger = LoggerFactory.getLogger(IOCallBackAction.class);

    /**
     * 处理IO行为game-stage中定义具体的行为
     * */
    private IOAction ioAction;

    public void response(IOResult ioResult) {
        if (ioResult != null) {
            ioAction.response(ioResult);
        }
    }

    public void answerExp(Throwable throwable) {
        logger.error("出现了一个异常:{}", throwable.getMessage());
    }

    public void setIoAction(IOAction ioAction) {
        this.ioAction = ioAction;
    }
}
