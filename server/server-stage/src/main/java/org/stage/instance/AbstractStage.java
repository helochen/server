package org.stage.instance;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.share.manager.impl.ChannelManager;

/**
 * Class:  AbstractStage
 * Author: word
 * Date:   2018/11/28 0:34
 */
public abstract class AbstractStage implements IStage {

    // 该场景内所有的角色Channels
    private ChannelGroup roleChannels;

    // 场景名称
    private String stageName;

    // 场景id
    private String stageId;

    @Override
    public void addRole(String roleId) {
        Channel roleChannel = ChannelManager.StageManagerInstance().getChannelByRoleId(roleId);
        roleChannel.closeFuture().addListener(
                future -> roleChannels.writeAndFlush("role leave stage!")
        );
    }

    @Override
    public void send() {
        roleChannels.flush();
    }

    @Override
    public void write(byte[] data) {
        roleChannels.write(data);
    }

    @Override
    public void writeAndSend(ByteBuf byteBuf) {
        roleChannels.writeAndFlush(byteBuf);
    }

    @Override
    public void writeAndSend(byte[] data) {
        roleChannels.writeAndFlush(data);
    }
}
