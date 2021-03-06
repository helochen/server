package org.server.demo.run;

import com.connect.gate.GateServer;
import com.connect.gate.rpc.rpchanlderImpl.BaseRpcHandler;
import com.exchange.NodeSwapLinkFactory;
import com.exchange.NodeSwapManager;
import com.exchange.register.services.IBusiness;
import org.share.tunnel.IIOTunnel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:/*-spring.xml");

        IBusiness business = applicationContext.getBean(IBusiness.class);
	    IIOTunnel ioAction = applicationContext.getBean(IIOTunnel.class);

        NodeSwapManager nodeSwapManager = NodeSwapLinkFactory.LinkServices(business, ioAction);

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
