package com.exchange.manager;

import com.exchange.manager.pool.GroupServicePoolDispatcher;
import com.exchange.manager.session.SessionManager;
import io.netty.channel.Channel;

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

    /*TODO*/
    public void activeSession(io.netty.channel.Channel remote) {
        System.out.println("activeSession........:D");

    }

    public void dispatcher(Object[] data) {
        groupServicePoolDispatcher.dispatcherMsg(data);
    }

    public void inactiveSession(Channel remote) {
        System.out.println("inactiveSession........:D");
    }
}
