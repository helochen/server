package com.exchange;

import com.exchange.pool.GroupServicePoolDispatcher;
import com.exchange.pool.factory.business.factory.RunnableFactory;
import com.exchange.pool.factory.business.factory.RunnableFactoryImpl;
import com.exchange.pool.factory.business.executor.IServiceExecuor;
import com.exchange.pool.factory.business.executor.ServiceExecutorImpl;
import com.exchange.pool.factory.io.controller.action.IOCallBackAction;
import com.exchange.pool.factory.io.controller.handler.IOResultFutureHandler;
import com.exchange.pool.factory.io.executor.IIOExecutor;
import com.exchange.pool.factory.io.executor.IOSingleThreadPool;
import com.exchange.register.services.IBusiness;
import org.share.tunnel.IIOTunnel;
import org.share.tunnel.PublicMsgSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class NodeSwapManagerRun
 * function: 就是初始化一些变量，互相依赖的太恶心，没办法。就这水平吧
 *
 * @Author chens
 * @Date 2018/7/8
 */
public class NodeSwapLinkFactory {

    private static final Logger logger = LoggerFactory.getLogger(NodeSwapManager.class);

    public static NodeSwapManager LinkServices(IBusiness business, IIOTunnel ioAction) {
        NodeSwapManager nodeSwapManager = null;

        if (business == null || ioAction == null) {
            logger.error("create nodeSwapManager failed! some key parameters is null!");
        } else {

            IOCallBackAction ioCallBackAction = new IOCallBackAction();

            ioCallBackAction.setIioTunnel(ioAction);

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

            /*这里可以让业务线程随意的推送信息给客户段*/
            PublicMsgSender.setIoTunnel(ioAction);

            /**
             * 
             * 通过创建这个对象GroupServicePoolDispatcher,可以实现业务给业务发送消息，也可以实现场景给业务发送消息
             * 实现PublicMsgSender的另外一个方法可以让他们可以互相的传递消息
             * 想法：
             *     直接通过获取groupServicePoolDispatcher发送新的事件
             *     业务代码内通过构建正确的Message msg对象
             *     groupServicePoolDispatcher.dispatcherMsg(msg)
             * */
            PublicMsgSender.setBusinessServiceDispather(groupServicePoolDispatcher);

            logger.info("NodeSwapManager create Instance....");

            /**
             *
             * TODO 如何正确的管理场景呢？
             *
             * */
        }

        return nodeSwapManager;
    }
}
