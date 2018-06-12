package org.stage.session;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.stage.IStage;
import org.stage.session.constants.StageChannelGroupType;

import java.util.Vector;

/**
 * 创建场景，场景的信息，哪些用户在该场景内
 * @Author chen
 * @Date  2018/06/12
 */
public class StageSession implements IStage {

    private ChannelGroup stageChannels;

    public StageSession(StageChannelGroupType type) {
        switch (type) {
            case GLOBAL_GROUP:
                stageChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                break;
            case NEVER_USE:
            case SINGLE_GROUP:
            default:
                stageChannels = new DefaultChannelGroup(new DefaultEventExecutor());
                break;

        }
    }

    /**
     * 是否支持自由退出
     */
    private boolean quitFreedom;
    /**
     * 场景内用户ID
     */
    private Vector<Long> roles = new Vector<>(20);

    /**
     * 给场景内的channels发送消息
     */
    @Override
    public void send() {
        stageChannels.flush();
    }

    /**
     * 场景内写入数据
     *
     * @param data
     */
    @Override
    public void write(byte[] data) {
        stageChannels.write(data);
    }

    /**
     * 写即刻发送
     *
     * @param data
     */
    @Override
    public void writeAndSend(byte[] data) {
        stageChannels.writeAndFlush(data);
    }

    /**
     * 是否在场景内,用户的ID
     *
     * @param roleId
     */
    @Override
    public boolean checkInStage(long roleId) {
        return false;
    }

    /**
     * 加入场景
     *
     * @param roleId
     */
    @Override
    public boolean joinStage(long roleId) {
        return roles.add(roleId);
    }

    /**
     * 退出场景
     *
     * @param roleId
     */
    @Override
    public boolean exitStage(long roleId) {
        return roles.remove(roleId);
    }

}