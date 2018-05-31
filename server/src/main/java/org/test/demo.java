package org.test;

import com.connect.gate.GateServer;
import com.connect.gate.rpc.IRpcHandler;
import com.connect.gate.rpc.rpchanlderImpl.BaseRpcHandler;
import com.exchange.manager.NodeSwapManager;
import com.service.controller.RunnableFactory;
import com.service.controller.impl.BusinessController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class demo {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:*-spring.xml");


        NodeSwapManager nodeSwapManager = applicationContext.getBean(NodeSwapManager.class);

        IRpcHandler rpcHandler = new BaseRpcHandler(nodeSwapManager);

        final GateServer gateServer = new GateServer("127.0.0.1",1024,2000,rpcHandler);


        try {
            gateServer.startAsync();
            gateServer.awaitRunning();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (gateServer.isRunning()) {
                        gateServer.stopAsync();
                        gateServer.awaitTerminated();
                    }
                }
            });
        } catch (Throwable e) {
        }
    }


}
