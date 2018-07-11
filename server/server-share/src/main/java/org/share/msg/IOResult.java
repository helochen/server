package org.share.msg;


import org.share.constants.IOMsgType;

/**
 * Game-service服务将返回的消息封装Message对象
 * 通过对象获取信息，通过Game-gate的IOMsgDisptcher 发送给客户端
 * 是否需要实现server-stage标识对象所在的stage，方便将所有的消息发送给stage内的对象
 *
 * @Author chen
 * @Date 22：52 2018-06-06
 */
public final class IOResult {

    private String stageId;

    private String command;

    private Object source;

    private String sessionId;
    /**
     * 返回数据的目标
     */
    private String[] targets;

    /**
     * IOMsgType 消息类型
     */
    private IOMsgType ioMsgType;

    private IOResult(String cmd, String stage, Object src) {
        this.command = cmd;
        this.source = src;
        this.stageId = stage;
        ioMsgType = IOMsgType.NEVER_USE;
        targets = null;
    }

    /**
     * 创建的按类型
     *
     * @param cmd
     * @param stageId
     * @param src
     * @param ioMsgType
     */
    private IOResult(String cmd, String stageId, Object src, IOMsgType ioMsgType) {
        this(cmd, stageId, src);
        this.ioMsgType = ioMsgType;
    }

    /**
     * 额外的指定的用户
     *
     * @param cmd
     * @param stageId
     * @param src
     * @param ioMsgType
     * @param targetIds
     */
    private IOResult(String cmd, String stageId, Object src, IOMsgType ioMsgType, String... targetIds) {
        this(cmd, stageId, src, ioMsgType);
        this.targets = targetIds;
    }

    public String getCommand() {
        return command;
    }

    public Object getSource() {
        return source;
    }

    public String getStageId() {
        return stageId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cmd:").append(command).append("\nstage:").append(stageId).append("\nsource:").append(source);
        return stringBuilder.toString();
    }

    public IOMsgType IoMsgType() {
        return ioMsgType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String[] getTargets() {
        return targets;
    }

    public static class Builder {
        /**
         * 给全链接用户消息
         *
         * @param cmd
         * @param src
         * @return
         */
        public static IOResult WorldIOResult(String cmd, Object src) {
            return new IOResult(cmd, null, src, IOMsgType.WORLD_IO_MSG);
        }

        /**
         * 给全场景用户发送消息对象
         *
         * @param cmd
         * @param stageId
         * @param src
         * @return
         */
        public static IOResult StageIOResult(String cmd, String stageId, Object src) {
            return new IOResult(cmd, stageId, src, IOMsgType.STAGE_IO_MSG);
        }

        /**
         * 给指定目标发送消息对象
         *
         * @param cmd
         * @param stageId
         * @param src
         * @param targetIds
         * @return
         */
        public static IOResult TargetIOResult(String cmd, String stageId, Object src, String... targetIds) {
            return new IOResult(cmd, stageId, src, IOMsgType.TARGET_IO_MSG, targetIds);
        }

        /**
         * 给当前的连接用户发送消息对象
         *
         * @param cmd
         * @param stageId
         * @param src
         * @return
         */
        public static IOResult SelfIOResult(String cmd, String stageId, Object src) {
            return new IOResult(cmd, stageId, src, IOMsgType.SELF_IO_MSG);
        }

        /**
         * 关闭当前的Channel
         */
        public static IOResult ShutDownSessionIOResult() {
            return new IOResult(null, null, null, IOMsgType.SHUTDOWN_CHANNEL_MSG);
        }
    }
}
