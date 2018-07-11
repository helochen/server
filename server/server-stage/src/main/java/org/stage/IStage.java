package org.stage;

import io.netty.buffer.ByteBuf;

/**
 * 负责场景内channel的服务功能
 * @Author chen
 * @Date 2018/06/10
 */
public interface IStage {

    /**
     * 给场景内的channels发送消息
     */
    void send();

    /**
     *
     * 场景内写入数据
     */
    void write(byte[] data);

    /**
     * 写数据ByteBuf
     */
    void writeAndSend(ByteBuf byteBuf);
    /**
     * 是否在场景内,用户的ID
     */
    boolean checkInStage(long roleId);

    /**
     * 加入场景
     */
    boolean joinStage(long roleId);

    /**
     * 退出场景
     */
    boolean exitStage(long roleId);
}
