package org.share.tunnel;

import org.share.msg.Message;

/**
 * class IBusinessServiceDispather
 * function:
 *
 * @Author chens
 * @Date 2018/8/5 20:34
 */
public interface IBusinessServiceDispather {

    /**
     * 直接自己构建消息发送消息
     * */
    void dispatcherMsg(Message msg);
}
