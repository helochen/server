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
public class IOResult {

    private Object stage;

    private Object command;

    private Object source;

    /**
     * 返回数据的目标
     */
    private Object[] targets;

    /**
     * IOMsgType 消息类型
     */
    private IOMsgType ioMsgType;

    private IOResult(Object cmd, Object stage, Object src) {
        this.command = cmd;
        this.source = src;
        this.stage = stage;
        ioMsgType = IOMsgType.NEVER_USE;
        targets = null;
    }

    /**
     * 创建的按类型
     * @param cmd
     * @param stage
     * @param src
     * @param ioMsgType
     */
    private IOResult(Object cmd, Object stage, Object src, IOMsgType ioMsgType) {
        this(cmd, stage, src);
        this.ioMsgType = ioMsgType;
    }

    /**
     * 额外的指定的用户
     * @param cmd
     * @param stage
     * @param src
     * @param ioMsgType
     * @param targets
     */
    private IOResult(Object cmd, Object stage, Object src, IOMsgType ioMsgType, Object... targets) {
        this(cmd, stage, src, ioMsgType);
        this.targets = targets;
    }

    public Object getCommand() {
        return command;
    }

    public Object getSource() {
        return source;
    }

    public Object getStage() {
        return stage;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("cmd:").append(command).append("\nstage:").append(stage).append("\nsource:").append(source);
        return stringBuilder.toString();
    }

    public static class IOResultHelper {
        /**
         * 给全链接用户消息
         * @param cmd
         * @param stage
         * @param src
         * @return
         */
        public static IOResult WorldMessageObj(Object cmd, Object stage, Object src) {
            return new IOResult(cmd,  stage,src , IOMsgType.WORLD_IO_MSG);
        }

        /**
         * 给全场景用户发送消息对象
         * @param cmd
         * @param stage
         * @param src
         * @return
         */
        public static IOResult StageMessageObj(Object cmd, Object stage, Object src) {
            return new IOResult(cmd, stage, src, IOMsgType.STAGE_IO_MSG);
        }

        /**
         * 给指定目标发送消息对象
         * @param cmd
         * @param stage
         * @param src
         * @param targets
         * @return
         */
        public static IOResult TargetMessageObj(Object cmd, Object stage, Object src, Object... targets) {
            return new IOResult(cmd, stage, src, IOMsgType.TARGET_IO_MSG, targets);
        }

        /**
         * 给当前的连接用户发送消息对象
         * @param cmd
         * @param stage
         * @param src
         * @return
         */
        public static IOResult SelfMessageObj(Object cmd, Object stage, Object src) {
            return new IOResult(cmd, stage, src, IOMsgType.SELF_IO_MSG);
        }
    }
}
