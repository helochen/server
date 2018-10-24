package org.share.constants;

public enum IOMsgType {

    NEVER_USE,
    /**
     * 给指定目标的用户
     */
    TARGET_IO_MSG,
    /**
     * 给当前链接发送消息
     */
    SELF_IO_MSG,
    /**
     * 关闭当前的channel
     */
    SHUTDOWN_CHANNEL_MSG,
}
