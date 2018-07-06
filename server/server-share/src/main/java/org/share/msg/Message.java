package org.share.msg;

/**
 * 统一的传入到服务方法中的对象
 *
 * @Author chen
 * @Date 2018.6.20
 */
public final class Message {

    /**
     * 转换后得到的command
     */
    private String command;

    /**
     * Channel传递上的数据
     */
    private Object source;

    /**
     * SessionID 保存在Session中的ID
     */
    private String sessionId;

    /**
     * 该消息创建的事件
     */
    private long createTime = System.currentTimeMillis();

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sessionId:").append(sessionId).append("\tcreateTime:").append(createTime);
        return sb.toString();
    }

    /**
     * 信息标识位
     */
    private final byte flag;

    public byte getFlag() {
        return flag;
    }

    public Message(String sessionId, byte flag, String command, Object source) {
        this.source = source;
        this.sessionId = sessionId;
        this.command = command;
        this.flag = flag;
    }

    public Object getSource() {
        return source;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getCommand() {
        return command;
    }
}
