package org.share.msg;

/**
 * 统一的传入到服务方法中的对象
 * @Author chen
 * @Date 2018.6.20
 */
public class Message {

    /**
     * Channel传递上的数据
     */
    private Object source;

    /**
     * SessionID 保存在Session中的ID
     */
    private String sessionId;

    /**
     * 登陆的用户StageId,所在的场景
     */
    private String stageId;

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
        sb.append("sessionId:").append(sessionId).append("\tstageId:").append(stageId).append("\tcreateTime:").append(createTime);
        return sb.toString();
    }

    public Message(String sessionId, String stageId, Object source) {
        this.source = source;
        this.sessionId = sessionId;
        this.stageId = stageId;
    }

    public Object getSource() {
        return source;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getStageId() {
        return stageId;
    }

    public long getCreateTime() {
        return createTime;
    }
}
