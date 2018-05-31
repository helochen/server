package org.prj.test;

import com.connect.gate.GateServer;
import com.connect.gate.rpc.IRpcHandler;
import io.netty.channel.Channel;

import java.io.IOException;

public class GateTest {


    public static void main(String[] arg) throws IOException {
        GateServer gateServer = new GateServer("127.0.0.1", 10218, 1000, new IRpcHandler<Object>() {

            @Override
            public void active(Channel remote) {
            }

            @Override
            public void handler(Channel remote, Object data) {

            }

            @Override
            public void inactive(Channel remote) {

            }
        });

        gateServer.startAsync();
        gateServer.awaitRunning();
        System.out.println("finished.............");
    }
}
