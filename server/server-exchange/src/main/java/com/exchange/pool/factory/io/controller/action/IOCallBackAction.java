package com.exchange.pool.factory.io.controller.action;


import org.share.msg.IOResult;
import org.share.tunnel.IIOTunnel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 响应函数
 */
public class IOCallBackAction {

    private static final Logger logger = LoggerFactory.getLogger(IOCallBackAction.class);

    /**
     * 处理IO行为game-stage中定义具体的行为
     * */
    private IIOTunnel iioTunnel;

    public void response(IOResult ioResult) {
        if (ioResult != null) {
            iioTunnel.response(ioResult);
        }
    }

    public void answerExp(Throwable throwable) {
        logger.error("出现了一个异常:{}", throwable.getMessage());
    }

    public void setIioTunnel(IIOTunnel iioTunnel) {
        this.iioTunnel = iioTunnel;
    }
}
