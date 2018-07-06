package com.exchange.pool;

import com.exchange.pool.factory.business.controller.RunnableFactory;
import com.exchange.pool.factory.business.executor.IServiceExecuor;
import com.exchange.pool.factory.io.controller.handler.IOResultFutureHandler;
import com.google.common.util.concurrent.ListenableFuture;
import org.share.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.Callable;

/**
 * exchange的核心，
 * 为所有的请求做处理，整个项目的业务调度核心啦
 *
 * @Author chen
 * @Date 2018.6.25
 */
public class GroupServicePoolDispatcher {

    private static final Logger logger = LoggerFactory.getLogger("Component.Error");

    /**
     * guava回调线程池,返回处理业务线程的处理结果
     * 将处理结果返回给IO线程池
     */
    private IServiceExecuor serviceExecuor;


    public void setServiceExecuor(IServiceExecuor serviceExecuor) {
        this.serviceExecuor = serviceExecuor;
    }

    /**
     * 业务线程池控制器，game-service中的对象，Spring管理的，
     * 创建业务线程对象的管理器
     */
    private RunnableFactory runnableFactory;


    public void setRunnableFactory(RunnableFactory runnableFactory) {
        this.runnableFactory = runnableFactory;
    }


    /**
     * 对业务线程的IO回调的处理
     */
    private IOResultFutureHandler ioResultFutureHandler;

    /**
     * 真实的分发处理消息的入口
     */
    public void dispatcherMsg(Message msg) {
        if (runnableFactory != null) {
            /**
             *
             * */
            Callable service = runnableFactory.getRunnable(msg);
            String group = runnableFactory.getGroup(msg.getCommand());

            if (service != null || StringUtils.isEmpty(group)) {
                logger.error("Component.Error, runnable factory cant get service thread", msg.getCommand());
            } else {
                ListenableFuture future = serviceExecuor.execute(group, service);
                /**
                 * IO回调在这里，是封装这个方法
                 * */
                ioResultFutureHandler.addFutureHandler(future);
            }

        } else {
            logger.error("Component.Error, BusinessController {} is null", GroupServicePoolDispatcher.class);
        }
    }

    public void setIoResultFutureHandler(IOResultFutureHandler ioResultFutureHandler) {
        this.ioResultFutureHandler = ioResultFutureHandler;
    }
}
