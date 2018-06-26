package com.exchange.pool.factory.business.controller;

import com.service.controller.impl.BusinessController;
import org.share.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 从game-server中获取业务线程的bean
 */
public class RunnableFactoryImpl implements RunnableFactory {

    private static final Logger logger = LoggerFactory.getLogger(RunnableFactoryImpl.class);

    private BusinessController businessController;

    public RunnableFactoryImpl(BusinessController businessController) {
        this.businessController = businessController;
    }


    @Override
    public Runnable getRunnable(String command, Message msg) {
        BusinessController.RouteInfo routeInfo = businessController.getCommandInfo().get(command);
        if (routeInfo != null) {
            if (routeInfo.isDeprecated()) {
                logger.error("客户端获取放弃的映射command:{}, bean Clazz:{}", command, routeInfo.getClazz());
                return null;
            } else {
                Method method = routeInfo.getMethod();
                Object bean = routeInfo.getBean();
                return () -> {
                    try {
                        Object value = method.invoke(bean, msg);
                        /**
                         * void会返回null值
                         */
                        if (value != null) {
                            /**
                             * TODO 返回Message对象(前面做了约定)，处理Message对象
                             */
                            System.out.println("result:\t" + value.toString());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.error("不好了，出现错误了BusinessController->getRunnalbe:{}", command);
                    }
                };
            }
        } else {
            logger.error("客户端获取未设置的映射command:{}", command);
            return null;
        }
    }

    @Override
    public String getGroup(String command) {
        return businessController.getCommandModule().get(command);
    }
}
