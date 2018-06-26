package org.share.constants;

public enum IOMsgType {

    NEVER_USE,
    /**
     * 给管理器内所有的用户发送消息
     */
    WORLD_IO_MSG,
    /**
     * 给指定的场景内的用户发送消息
     */
    STAGE_IO_MSG,
    /**
     * 给指定目标的用户
     */
    TARGET_IO_MSG,
    /**
     * 给当前链接发送消息
     */
    SELF_IO_MSG,
}
