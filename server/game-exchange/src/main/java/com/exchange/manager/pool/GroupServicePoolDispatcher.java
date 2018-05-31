package com.exchange.manager.pool;

import com.exchange.manager.pool.factory.IPoolFactory;
import com.service.controller.RunnableFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class GroupServicePoolDispatcher {

    private static final Logger logger = LoggerFactory.getLogger("Component.Error");

    private IPoolFactory poolFactory;

    public void setPoolFactory(IPoolFactory poolFactory) {
        this.poolFactory = poolFactory;
    }

    /**业务线程池控制器*/
    private RunnableFactory runnableFactory;


    public void setRunnableFactory(RunnableFactory runnableFactory) {
        this.runnableFactory = runnableFactory;
    }

    public void dispatcherMsg(Object[] data) {
        if (runnableFactory != null) {
            String command = (String) data[0];

            Runnable service = runnableFactory.getRunnable(command, data[1]);
            String group = runnableFactory.getGroup(command);

            if (service != null || StringUtils.isEmpty(group)) {
                logger.error("Component.Error, runablefactory cant get service thread", command);
            } else {
                poolFactory.execute(group, service);
            }

        } else {
            logger.error("Component.Error, BusinessController {} is null", GroupServicePoolDispatcher.class);
        }
    }
}
