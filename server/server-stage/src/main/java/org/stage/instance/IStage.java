package org.stage.instance;

import io.netty.buffer.ByteBuf;

/**
 * 负责场景内channel的服务功能
 * @Author chen
 * @Date 2018/06/10
 */
public interface IStage {

    /**
     * 当一个用户加入场景的时候操作
     * */
    void addRole(String roleId);

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
     *
     * */
    void writeAndSend(byte[] data);

}
