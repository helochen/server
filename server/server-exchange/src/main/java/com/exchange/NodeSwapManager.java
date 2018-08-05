package com.exchange;

import com.exchange.pool.GroupServicePoolDispatcher;
import io.netty.channel.Channel;
import org.share.manager.IExChangeManager;
import org.share.manager.impl.ChannelManager;
import org.share.msg.Message;
import org.share.tunnel.IBusinessServiceDispather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeSwapManager {

    private static final Logger logger = LoggerFactory.getLogger(NodeSwapManager.class);

    public NodeSwapManager(GroupServicePoolDispatcher dispatcher) {
        this.groupServicePoolDispatcher = dispatcher;
    }
    /**
     * 负责业务
     * */
    private IBusinessServiceDispather groupServicePoolDispatcher;

    /**
     * Channel容器,单例
     */
    private IExChangeManager exChangeManager = ChannelManager.getInstance();


    public void setGroupServicePoolDispatcher(GroupServicePoolDispatcher groupServicePoolDispatcher) {
        this.groupServicePoolDispatcher = groupServicePoolDispatcher;
    }


    public void activeSession(Channel remote) {
        exChangeManager.acitve(remote);
        logger.info("activeSession........:D");
    }

    public void dispatcher(Channel remote, Object[] data) {
        /**
         * 转换Channel数据
         * */
        Message msgObj = exChangeManager.conver2MsgObj(remote , data);
        /**
         * 转换为Message对象传给服务
         * */
        groupServicePoolDispatcher.dispatcherMsg(msgObj);
    }

    public void inactiveSession(Channel remote) {
        exChangeManager.inactive(remote);
        logger.info("inactiveSession........:D");
    }
}
