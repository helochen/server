package org.stage.session;

import io.netty.buffer.ByteBuf;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.share.cache.ICache;
import org.share.util.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stage.IStage;
import org.stage.session.constants.StageChannelGroupType;

import java.util.Vector;

/**
 * 创建场景，场景的信息，哪些用户在该场景内
 * @Author chen
 * @Date  2018/06/12
 */
public class StageSession implements IStage , ICache {

    private static final Logger logger = LoggerFactory.getLogger(StageSession.class);

    public static final String WORLD_STAGE_KEY = KeyUtil.stringKey();

    private String stageId;

    private ChannelGroup stageChannels;

    public StageSession(StageChannelGroupType type) {
        switch (type) {
            case GLOBAL_GROUP:
                /**
                 * 有这个的原因是默认有个全局的场景
                 **/
                stageChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                stageId = WORLD_STAGE_KEY;
                logger.info("创建GLOBAL-STAGE：key:{}", stageId);
                break;
            case NEVER_USE:
            case SINGLE_GROUP:
            default:
                stageChannels = new DefaultChannelGroup(new DefaultEventExecutor());
                stageId = KeyUtil.stringKey();
                logger.info("创建NORMAL-STAGE：key:{}", stageId);
                break;
        }


    }

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
     * 写数据ByteBuf
     *
     * @param byteBuf
     */
    @Override
    public void writeAndSend(ByteBuf byteBuf) {
        stageChannels.writeAndFlush(byteBuf);
    }

    /**
     * 场景的实力允许被CacheManager管理
     * @return
     */
    @Override
    public Object getKey() {
        return stageId;
    }
}
