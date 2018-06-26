package com.exchange.pool;

import com.exchange.pool.factory.business.controller.RunnableFactory;
import com.exchange.pool.factory.business.executor.IServiceExecuor;
import com.exchange.pool.factory.io.IIOExecutor;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.io.handler.IOResultFutureHandler;
import org.share.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * exchage的核心，
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
     * io处理guava回调的线程池
     */
    private IIOExecutor ioExecutor;


    public void setIoExecutor(IIOExecutor ioExecutor) {
        this.ioExecutor = ioExecutor;
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
     * IO回调的处理函数哦
     */
    private IOResultFutureHandler ioResultFutureHandler;

    /**
     * 真实的分发处理消息的入口
     */
    public void dispatcherMsg(Object[] data) {
        if (runnableFactory != null) {
            String command = (String) data[0];
            /**
             * TODO
             * 转换为message
             */
            Runnable service = runnableFactory.getRunnable(command, new Message("", "", data[1]));
            String group = runnableFactory.getGroup(command);

            if (service != null || StringUtils.isEmpty(group)) {
                logger.error("Component.Error, runnable factory cant get service thread", command);
            } else {
                ListenableFuture future = serviceExecuor.execute(group, service);
                /*TODO 是封装这个方法*/
                Futures.addCallback(future, ioResultFutureHandler, ioExecutor.getExecutor());
            }

        } else {
            logger.error("Component.Error, BusinessController {} is null", GroupServicePoolDispatcher.class);
        }
    }

}
