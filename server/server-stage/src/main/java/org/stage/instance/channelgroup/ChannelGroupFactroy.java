package org.stage.instance.channelgroup;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.stage.session.constants.StageChannelGroupType;

/**
 * Class:  ChannelGroupFactroy
 * Author: word
 * Date:   2018/11/28 1:19
 */
public class ChannelGroupFactroy {

    public static ChannelGroup Builder(StageChannelGroupType type) {
        ChannelGroup channels = null;
        switch (type) {
            case NEVER_USE:
                break;
            case GLOBAL_GROUP:
                channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                break;
            case SINGLE_GROUP:
                channels = new NoticeChannelGroup(GlobalEventExecutor.INSTANCE, null);
                break;
        }
        return channels;
    }

}
