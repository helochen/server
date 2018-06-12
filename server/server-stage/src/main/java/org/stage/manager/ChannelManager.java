package org.stage.manager;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelManager {

    private static Logger logger = LoggerFactory.getLogger(ChannelManager.class);

    private static ChannelManager ourInstance = new ChannelManager();

    public static ChannelManager getInstance() {
        return ourInstance;
    }

    private ChannelManager() {
    }

    private Map<Channel, InfoSession> channelMap = new ConcurrentHashMap<>();

    /**
     * active,用户链接
     */
    public void acitve(Channel channel) {
        channelMap.put(channel, new InfoSession());
    }

    /**
     * inactvie,用户断开链接
     */
    public void inactive(Channel channel) {
        channelMap.remove(channel);
    }


    public class InfoSession {
        private long roleId;

        private String userId;

        private String StageId;

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
    }
}
