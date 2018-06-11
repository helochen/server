package org.share.msg;


/**
 * Game-service服务将返回的消息封装Message对象
 * 通过对象获取信息，通过Game-gate的IOMsgDisptcher 发送给客户端
 * 是否需要实现server-stage标识对象所在的stage，方便将所有的消息发送给stage内的对象
 *
 * @Author chen
 * @Date 22：52 2018-06-06
 */
public class Message {

    private Object stage;

    private Object command;

    private Object source;

    private Message(Object cmd, Object src, Object stage) {
        this.command = cmd;
        this.source = src;
        this.stage = stage;
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

    public static class MessageFactory {
        public static Message getMessageObj(Object stage, Object cmd, Object src) {
            return new Message(cmd, src, stage);
        }
    }
}
