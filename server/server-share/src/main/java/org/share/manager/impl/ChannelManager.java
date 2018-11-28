package org.share.manager.impl;

import io.netty.channel.Channel;
import org.share.command.FlagType;
import org.share.manager.IBusinessManager;
import org.share.manager.IExChangeManager;
import org.share.manager.IStageManager;
import org.share.msg.Message;
import org.share.util.KeyUtil;
import org.share.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChannelManager 登陆信息管理
 *
 * @Author chen
 * @Date 2018.6.20
 */
public class ChannelManager implements IExChangeManager {

    private static Logger logger = LoggerFactory.getLogger(ChannelManager.class);

    private static ChannelManager ourInstance = new ChannelManager();

    public static ChannelManager getInstance() {
        return ourInstance;
    }

    private ChannelManager() {
    }

    public static IBusinessManager BusinessManagerInstance() {
        return ourInstance.infoStorageInstance;
    }

    public static IStageManager StageManagerInstance() {
        return ourInstance.infoStorageInstance;
    }

    // 单独放
    private InfoStorageInstance infoStorageInstance = new InfoStorageInstance();

    /**
     * active,用户链接
     */
    @Override
    public void active(Channel channel) {
        InfoSession infoSession = new InfoSession(channel);
        this.infoStorageInstance.channelMap.put(channel, infoSession);
        this.infoStorageInstance.sessionIdMap.put(infoSession.getSessionId(), infoSession);
    }


    /**
     * inactive,用户断开链接
     */
    @Override
    public void inactive(Channel channel) {
        InfoSession infoSession = this.infoStorageInstance.channelMap.remove(channel);
        if (infoSession != null) {
            logger.info("ChannelManager clear memory......");
            this.infoStorageInstance.sessionIdMap.remove(infoSession.getSessionId());
            this.infoStorageInstance.roleIdMap.remove(infoSession.getRoleId());
        } else {
            logger.warn("ChannelManager remove channel return null");
        }
    }

    /**
     * 得到SessionId
     *
     * @param channel
     * @return
     */
    public String getSessionId(Channel channel) {
        return this.infoStorageInstance.channelMap.get(channel) == null ? null : this.infoStorageInstance.channelMap.get(channel).getSessionId();
    }

    /**
     * 转换信息
     */
    @Override
    public Message conver2MsgObj(Channel remote, Object[] data) {
        Message msg = null;
        InfoSession infoSession = this.infoStorageInstance.channelMap.get(remote);
        if (infoSession != null) {
            msg = new Message(infoSession.sessionId, infoSession.flag, ObjectUtil.obj2StrOrNull(data[0]), data[1]);
        }
        return msg;
    }


    private class InfoSession {
        private long roleId;

        private String userId;

        private Channel channel;

        private String sessionId;
        /**
         * 检查位,用于记录一些标识信息，
         * 比如接受到一个操作，没有登陆就直接发送
         * 新创建的的就会放弃这个信息
         */
        private byte flag;
        /**
         * 系统对象的创建的时间
         */
        private long createTime = System.currentTimeMillis();


        public InfoSession(Channel channel) {
            flag = FlagType.ACTIVE_INIT;
            this.channel = channel;
            sessionId = KeyUtil.stringKey();
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

        public void setExtraParams(String key, String value) {
            params.put(key, value);
        }

        public String getSessionId() {
            return sessionId;
        }

        public Channel getChannel() {
            return channel;
        }

        protected void setFlag(byte flag) {
            this.flag = flag;
        }
    }


    private class InfoStorageInstance implements IStageManager, IBusinessManager {
        private Map<Channel, InfoSession> channelMap = new ConcurrentHashMap<>();
        /**
         * 通过SessionId获得用户信息
         */
        private Map<String, InfoSession> sessionIdMap = new ConcurrentHashMap<>();

        /**
         * 通过RoleId获得用户信息
         */
        private Map<String, InfoSession> roleIdMap = new ConcurrentHashMap<>();

        private InfoStorageInstance() {

        }

        /**
         * 将对象放入RoleId
         *
         * @param sessionId
         * @param roleId
         */
        @Override
        public void updateRoleIdInfo(String sessionId, String roleId) {
            InfoSession infoSession;
            if (roleIdMap.containsKey(roleId)) {
                logger.info("login again....roleId:{}", roleId);
                /*得到之前的sessionInfo*/
                infoSession = roleIdMap.get(roleId);
                if (infoSession != null) {
                    logger.warn("这里用户重新登陆过了.......roleId:{}", roleId);
                    /*移除之前的channel*/
                    Channel channel = infoSession.getChannel();
                    if (null != channel && channel.isActive()) {
                        channel.close();
                    }
                }

            }
            infoSession = sessionIdMap.get(sessionId);
            if (infoSession != null) {
                infoSession.setFlag(FlagType.LOGIN_SUCCESS);
                roleIdMap.put(roleId, infoSession);
                logger.info("role login success! update the session flag,role:{}", roleId);
            } else {
                logger.error("updateRoleInfo failed! cant find session");
            }

        }

        /**
         * 删除退出
         *
         * @param roleId
         */
        @Override
        public boolean checkLoginStatus(String roleId) {
            return roleIdMap.containsKey(roleId);
        }


        /**
         * 给stage模块用的咯
         * 可以得到具体的Channel咯
         */
        @Override
        public Channel getChannelBySessionId(String sessionId) {
            return sessionIdMap.get(sessionId).getChannel();
        }

        /**
         * * 给stage模块用的咯
         * 可以得到具体的Channel咯
         */
        @Override
        public Channel getChannelByRoleId(String roleId) {
            return roleIdMap.get(roleId).getChannel();
        }

        /**
         * 释放空间
         */
        @Override
        public void releaseSession(String sessionId) {
            InfoSession infoSession = sessionIdMap.remove(sessionId);
            if (infoSession != null) {
                channelMap.remove(infoSession.getChannel());
                roleIdMap.remove(infoSession.getRoleId());
            }
        }
    }
}
