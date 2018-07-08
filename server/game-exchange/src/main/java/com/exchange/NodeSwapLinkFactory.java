package com.exchange;

import com.exchange.pool.GroupServicePoolDispatcher;
import com.exchange.pool.factory.business.controller.RunnableFactory;
import com.exchange.pool.factory.business.controller.RunnableFactoryImpl;
import com.exchange.pool.factory.business.executor.IServiceExecuor;
import com.exchange.pool.factory.business.executor.ServiceExecutorImpl;
import com.exchange.pool.factory.io.controller.action.IOCallBackAction;
import com.exchange.pool.factory.io.controller.handler.IOResultFutureHandler;
import com.exchange.pool.factory.io.executor.IIOExecutor;
import com.exchange.pool.factory.io.executor.IOSingleThreadPool;
import com.service.controller.IBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stage.io.IOAction;

/**
 * class NodeSwapManagerRun
 * function:
 *
 * @Author chens
 * @Date 2018/7/8
 */
public class NodeSwapLinkFactory {

    private static final Logger logger = LoggerFactory.getLogger(NodeSwapManager.class);

    public static NodeSwapManager LinkServices(IBusiness business, IOAction ioAction) {
        NodeSwapManager nodeSwapManager = null;

        if (business == null || ioAction == null) {
            logger.error("create nodeswapMananger failed ! same parameters is null!");
        } else {

            IOCallBackAction ioCallBackAction = new IOCallBackAction();

            ioCallBackAction.setIoAction(ioAction);

            /*负责io的业务的线程池*/
            IIOExecutor iioExecutor = new IOSingleThreadPool(1, 10);

            /*IO处理回调*/
            IOResultFutureHandler ioResultFutureHandler = new IOResultFutureHandler();
            /*这个是IO的处理逻辑stage模块里面*/
            ioResultFutureHandler.setIoCallBackAction(ioCallBackAction);
            ioResultFutureHandler.setIoExecutor(iioExecutor);

            /*把业务线程的控制器*/
            RunnableFactory runnableFactory = new RunnableFactoryImpl(business);

            /*业务线程池服务*/
            IServiceExecuor serviceExecutor = new ServiceExecutorImpl();

            /*分发的对象*/
            GroupServicePoolDispatcher groupServicePoolDispatcher = new GroupServicePoolDispatcher();
            /*设置参数*/
            groupServicePoolDispatcher.setServiceExecuor(serviceExecutor);
            groupServicePoolDispatcher.setRunnableFactory(runnableFactory);
            groupServicePoolDispatcher.setIoResultFutureHandler(ioResultFutureHandler);

            /*就是要创建这个对象*/
            nodeSwapManager = new NodeSwapManager(groupServicePoolDispatcher);

            logger.info("NodeSwapManager create Instance....");
        }

        return nodeSwapManager;
    }
}
