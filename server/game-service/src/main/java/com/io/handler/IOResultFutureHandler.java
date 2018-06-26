package com.io.handler;

import com.google.common.util.concurrent.FutureCallback;
import com.io.action.IOCallBackAction;
import org.share.msg.IOResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 处理业务线程结果的回调方法
 *
 * @Author chen
 * @Date 2018.6.23
 */
@Service
public class IOResultFutureHandler implements FutureCallback<IOResult> {

    private static Logger logger = LoggerFactory.getLogger(IOResultFutureHandler.class);


    private IOCallBackAction ioCallBackAction;



    @Override
    public void onSuccess(IOResult ioResult) {
        if (ioResult != null) {
            System.out.println("ok" + ioResult);
            ioCallBackAction.respond(ioResult);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        logger.error("IOResultFuture throw an Exception : {}", throwable.getMessage());
        ioCallBackAction.answer(throwable);
    }
}
