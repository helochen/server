package org.share.tunnel;

import org.share.msg.IOResult;
import org.share.msg.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class PublicMsgSender
 * function: 负责业务的可以多发了，随意发
 *
 * @Author chens
 * @Date 2018/8/5 20:14
 */
public class PublicMsgSender {

    public static final Logger logger = LoggerFactory.getLogger(PublicMsgSender.class);

    private static IIOTunnel ioTunnel = null;

    private static IBusinessServiceDispather iBusinessServiceDispather;

    public static void setIoTunnel(IIOTunnel ioTunnel) {
        PublicMsgSender.ioTunnel = ioTunnel;
    }

    /**
     * 给客户段发消息
     * */
    public static void Send2Msg(IOResult ioResult) {
        if (ioTunnel == null) {
            logger.error("没有设置IOACTION对象吗？");
        } else {
            ioTunnel.response(ioResult);
        }
    }

    /**
     * 内部业务事件循环
     */
    public static void SendInnerMsg(Message msg) {
        iBusinessServiceDispather.dispatcherMsg(msg);
    }

    public static void setBusinessServiceDispather(IBusinessServiceDispather iBusinessServiceDispather) {
        PublicMsgSender.iBusinessServiceDispather = iBusinessServiceDispather;
    }
}
