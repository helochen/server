package org.share.manager;

import io.netty.channel.Channel;

/**
 * interface IStageManager
 * function:
 *
 * @Author chens
 * @Date 2018/7/7
 */
public interface IStageManager {

    Channel getChannelBySessionId(String sessionId);

    Channel getChannelByRoleId(String roleId);

    void releaseSession(String sessionId);
}
