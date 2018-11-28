package org.share.manager;

import io.netty.channel.Channel;
import org.share.msg.Message;

/**
 * interface IChannelManager
 * function:
 *
 * @Author chens
 * @Date 2018/7/6
 */
public interface IExChangeManager {


    Message conver2MsgObj(Channel remote, Object[] data);
    /**
     * 断开
     * */
    void inactive(Channel channel);
    /**
     * 链接初始化
     * */
    void active(Channel channel);

}
