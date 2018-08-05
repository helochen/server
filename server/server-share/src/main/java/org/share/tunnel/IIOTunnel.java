package org.share.tunnel;

import org.share.msg.IOResult;

/**
 * interface IIOTunnel
 * function:
 *
 * @Author chens
 * @Date 2018/8/5 20:15
 */
public interface IIOTunnel {

    /**
     * 给客户端回消息
     * */
    void response(IOResult ioResult);
}

