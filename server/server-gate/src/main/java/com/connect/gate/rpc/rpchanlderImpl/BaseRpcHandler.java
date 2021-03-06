package com.connect.gate.rpc.rpchanlderImpl;

import com.connect.gate.rpc.IRpcHandler;
import com.exchange.NodeSwapManager;
import io.netty.channel.Channel;

public class BaseRpcHandler extends AbstractRpcHandler implements IRpcHandler<Object> {

    private final NodeSwapManager nodeSwapManager ;

    public BaseRpcHandler(NodeSwapManager nodeSwapManager) {
        this.nodeSwapManager = nodeSwapManager;
    }

    @Override
    public void active(Channel remote) {
        getLogger().debug("channel:{} active....", remote);
        /* 链接进入，添加到MessageDispatcher内？*/
        nodeSwapManager.activeSession(remote);
    }

    @Override
    public void handler(Channel remote, Object data) {
        getLogger().info("channel:{} hanlder.........", remote);

        if (data instanceof Object[]) {
            /*做一个事件的分发*/
            nodeSwapManager.dispatcher(remote , (Object[])data);
        } else {
            getLogger().info("data{} is not Object[]", data);
        }
        //nodeSwapManager.dispatcher(remote , new Object[]{"10000" , "test"});
    }

    @Override
    public void inactive(Channel remote) {
        getLogger().debug("channel:{} inactive.......", remote);
        /*TODO 是否有必要*/
        nodeSwapManager.inactiveSession(remote);
    }
}
