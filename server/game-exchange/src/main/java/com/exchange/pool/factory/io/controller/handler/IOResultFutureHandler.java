package com.exchange.pool.factory.io.controller.handler;

import com.exchange.pool.factory.io.controller.action.IOCallBackAction;
import com.exchange.pool.factory.io.executor.IIOExecutor;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.share.msg.IOResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理业务线程结果的回调方法
 *
 * @Author chen
 * @Date 2018.6.23
 */
public class IOResultFutureHandler implements FutureCallback<IOResult> {

    private static Logger logger = LoggerFactory.getLogger(IOResultFutureHandler.class);

    /**
     * 处理IO行为game-stage中定义
     * */
    private IOCallBackAction ioCallBackAction;

    /**
     * io处理guava回调的线程池
     */
    private IIOExecutor ioExecutor;


    public void setIoExecutor(IIOExecutor ioExecutor) {
        this.ioExecutor = ioExecutor;
    }

    public void addFutureHandler(ListenableFuture future) {
        Futures.addCallback(future, this, ioExecutor.getExecutor());
    }

    @Override
    public void onSuccess(IOResult ioResult) {
        if (ioResult != null) {
            logger.info("ok!{}", ioResult);
            ioCallBackAction.response(ioResult);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        logger.error("IOResultFuture throw an Exception : {}", throwable.getMessage());
        ioCallBackAction.answerExp(throwable);
    }

    public void setIoCallBackAction(IOCallBackAction ioCallBackAction) {
        this.ioCallBackAction = ioCallBackAction;
    }
}
