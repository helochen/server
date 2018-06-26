package org.stage.manager;

import io.netty.channel.Channel;
import org.share.msg.Message;

/**
 * 转换登陆信息
 */
public class SessionInfoHelper {


    private static ChannelManager channelManager = ChannelManager.getInstance();

    /**
     * 头疼，如果不登陆直接发送消息会在内存创建一个对象，关闭的时候删除Map中保持的session对象，并且close掉Channel
     * @param channel
     * @param source 传上来的数据
     * @return Message 如果为空，断开Channel
     */
    public static Message convert2Message(Channel channel , Object source) {
        String sessionId = channelManager.getSessionId(channel);
        String stageId = channelManager.getStageId(channel);

        return sessionId == null || sessionId == null ? null : new Message(sessionId, stageId, source);
    }


}
