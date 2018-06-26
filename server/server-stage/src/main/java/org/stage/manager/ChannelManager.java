package org.stage.manager;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChannelManager 登陆信息管理
 * @Author chen
 * @Date 2018.6.20
 */
public class ChannelManager {

    private static Logger logger = LoggerFactory.getLogger(ChannelManager.class);

    private static ChannelManager ourInstance = new ChannelManager();

    public static ChannelManager getInstance() {
        return ourInstance;
    }

    private ChannelManager() {
    }

    private Map<Channel, InfoSession> channelMap = new ConcurrentHashMap<>();

    private Map<String, InfoSession> sessionIdMap = new ConcurrentHashMap<>();

    /**
     * active,用户链接
     */
    public void acitve(Channel channel) {
        InfoSession infoSession  =  new InfoSession(channel);
        channelMap.put(channel, infoSession);
        sessionIdMap.put(infoSession.getSessionId(), infoSession);
    }

    /**
     * inactive,用户断开链接
     */
    public void inactive(Channel channel) {
        InfoSession infoSession = channelMap.remove(channel);
        if (infoSession != null) {
            sessionIdMap.remove(infoSession.getSessionId());
        } else {
            logger.warn("ChannelManager remove channel return null");
        }
    }

    /**
     * 得到场景的ID
     * @param channel
     * @return
     */
    public String getStageId(Channel channel) {
        return channelMap.get(channel) == null ? null : channelMap.get(channel).getStageId();
    }

    /**
     * 得到SessionId
     * @param channel
     * @return
     */
    public String getSessionId(Channel channel) {
        return channelMap.get(channel) == null ? null : channelMap.get(channel).getSessionId();
    }

    public class InfoSession {
        private long roleId;

        private String userId;

        private String StageId;

        private Channel channel;

        private String sessionId;
        /**
         * 系统对象的创建的时间
         */
        private long createTime = System.currentTimeMillis();


        public InfoSession(Channel channel) {
            this.channel = channel;
            sessionId = UUID.randomUUID().toString();
        }

        private Map<String, String> params = new HashMap<>(5);

        public long getRoleId() {
            return roleId;
        }

        public void setRoleId(long roleId) {
            this.roleId = roleId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStageId() {
            return StageId;
        }

        public void setStageId(String stageId) {
            StageId = stageId;
        }

        public void setExtraParams(String key, String value) {
            params.put(key, value);
        }

        public String getSessionId() {
            return sessionId;
        }

        public Channel getChannel() {
            return channel;
        }
    }
}
