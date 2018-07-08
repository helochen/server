package org.test;

import com.connect.gate.GateServer;
import com.connect.gate.rpc.rpchanlderImpl.BaseRpcHandler;

public class AppTest {

    public static void main(String[] args) {
        final GateServer gateServer = new GateServer("127.0.0.1", 1024, 1000, new BaseRpcHandler(null));

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
    }
}
