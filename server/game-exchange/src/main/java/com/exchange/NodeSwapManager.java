package com.exchange;

import com.exchange.pool.GroupServicePoolDispatcher;
import com.exchange.session.SessionManager;
import io.netty.channel.Channel;
import org.stage.manager.ChannelManager;

public class NodeSwapManager {

    /*负责业务*/
    private GroupServicePoolDispatcher groupServicePoolDispatcher;

    /*保持session*/
    private SessionManager sessionManager;

    public void setGroupServicePoolDispatcher(GroupServicePoolDispatcher groupServicePoolDispatcher) {
        this.groupServicePoolDispatcher = groupServicePoolDispatcher;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    public void activeSession(io.netty.channel.Channel remote) {
        ChannelManager.getInstance().acitve(remote);
        System.out.println("activeSession........:D");
    }

    public void dispatcher(Object[] data) {
        groupServicePoolDispatcher.dispatcherMsg(data);
    }

    public void inactiveSession(Channel remote) {
        ChannelManager.getInstance().inactive(remote);
        System.out.println("inactiveSession........:D");
    }
}