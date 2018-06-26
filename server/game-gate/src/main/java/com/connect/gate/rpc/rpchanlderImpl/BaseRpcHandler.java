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
        /*TODO 链接进入，添加到MessageDispatcher内？*/
        nodeSwapManager.activeSession(remote);
    }

    @Override
    public void handler(Channel remote, Object data) {
        getLogger().debug("channel:{} hanlder.........", remote);
        if (data instanceof Object[]) {
            /*做一个事件的分发*/
            nodeSwapManager.dispatcher((Object[])data);
        } else {
            getLogger().debug("data{} is not Object[]", data);
        }
    }

    @Override
    public void inactive(Channel remote) {
        getLogger().debug("channel:{} inactive.......", remote);
        /*TODO 是否有必要*/
        nodeSwapManager.inactiveSession(remote);
    }
}
