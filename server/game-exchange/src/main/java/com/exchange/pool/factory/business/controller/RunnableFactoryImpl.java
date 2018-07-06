package com.exchange.pool.factory.business.controller;

import com.service.controller.IBusiness;
import com.service.controller.impl.BusinessController;
import org.share.msg.IOResult;
import org.share.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;


/**
 * 从game-server中获取业务线程的bean
 */
public class RunnableFactoryImpl implements RunnableFactory {

    private static final Logger logger = LoggerFactory.getLogger(RunnableFactoryImpl.class);

    private IBusiness businessController;

    public RunnableFactoryImpl(BusinessController businessController) {
        this.businessController = businessController;
    }

    @Override
    public Callable getRunnable(Message msg) {

        /**
         *
         * TODO 测试的条件写个函数，或者Enum类型
         * */
        if (businessController.checkflag(msg.getCommand(), msg.getFlag()) > 0) {

            Method method = businessController.getMethod(msg.getCommand());
            Object bean = businessController.getBean(msg.getCommand());
            if (method != null && bean != null) {
                return () -> {
                    Object result = null;
                    try {
                        result = method.invoke(bean, msg);
                        /**
                         * void会返回null值
                         */
                        if (result != null) {
                            /**
                             * TODO 返回IOResult对象(前面做了约定)，处理IOResult对象
                             */
                            logger.info("result:\t{}", result.toString());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("不好了，出现错误了BusinessController->getRunnalbe:{}", msg.getCommand());
                    } finally {
                        return result;
                    }
                };

            } else {
                logger.error("客户端获取未设置的映射command:{}", msg.getCommand());
                return null;
            }
        } else {
            logger.error("没有通过session中的flag位检测，是不是哪里有问题>>>>>command:{} flag:{}", msg.getCommand(), msg.getFlag());
            return shutDown;
        }
    }

    /**
     * 无限的进入而不多次构造对象
     * 通知Channel关闭,删除session
     */
    private Callable shutDown = () -> IOResult.Builder.ShutDownSessionIOResult();

    @Override
    public String getGroup(String command) {
        return businessController.getGroup(command);
    }
}
