package org.stage.session.constants;

public enum StageChannelGroupType {

    NEVER_USE,
    /**
     * 使用 GlobalEventExecutor INSTANCE 创建ChannelGroup
     * */
    GLOBAL_GROUP,
    /**
     * 使用DefaultEventExecutor 实例创建ChannelGroup
     * */
    SINGLE_GROUP,
}
