package org.server.demo.run;

import com.connect.gate.GateServer;
import com.connect.gate.rpc.rpchanlderImpl.BaseRpcHandler;
import com.exchange.manager.NodeSwapManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:/*-spring.xml");

        NodeSwapManager nodeSwapManager = applicationContext.getBean(NodeSwapManager.class);

        if (nodeSwapManager != null) {
            final GateServer gateServer = new GateServer("127.0.0.1", 1024, 1000, new BaseRpcHandler(nodeSwapManager));

            try {
                gateServer.startAsync();
                gateServer.awaitRunning();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (gateServer.isRunning()) {
                        gateServer.stopAsync();
                        gateServer.awaitTerminated();
                    }
                }));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("cant get bean from spring ioc");
        }



    }
}
