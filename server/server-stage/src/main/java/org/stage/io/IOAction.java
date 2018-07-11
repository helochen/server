package org.stage.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.share.constants.IOMsgType;
import org.share.manager.IStageManager;
import org.share.manager.impl.ChannelManager;
import org.share.msg.IOResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stage.IStage;
import org.stage.manager.impl.StageCacheManager;
import org.stage.session.StageSession;

/**
 * class IOAction
 * function: 处理IOResult对象
 *
 * @Author chens
 * @Date 2018/7/6
 */
public class IOAction {

    private static final Logger logger = LoggerFactory.getLogger(IOAction.class);

    /**
     * Stage 所有的对象缓存
     */
    private StageCacheManager stageManager;

    /**
     * 创建内存对象的管理器，我们可以得到具体的Channel
     */
    private IStageManager channelManager = ChannelManager.getInstance();

    public IOAction(StageCacheManager stageManager) {
        this.stageManager = stageManager;
    }

    public void response(IOResult ioResult) {

        ByteBuf target = null;
        if (ioResult.getSource() != null && ioResult.getSource() instanceof byte[]) {
            int length = ((byte[]) ioResult.getSource()).length;
            try {
                int cmd = Integer.parseInt(ioResult.getCommand());
                /**
                 * 消息的结构：长度|命令|数据byte[]
                 * 命令是字符串转换过来的
                 * */
                target = Unpooled.directBuffer(2 * 4 + length);

                target.writeInt(length).writeInt(cmd).writeBytes((byte[]) ioResult.getSource(), 0, length);
            } catch (Exception e) {
                logger.error("构造数据结果出错了，为甚呢？exception:{}", e.getLocalizedMessage());
                if (target != null) {
                    target.release();
                }
                return;
            }
        }

        if (ioResult.IoMsgType() == IOMsgType.SELF_IO_MSG) {

            String sessionId = ioResult.getSessionId();
            Channel remote = channelManager.getChannelBySessionId(sessionId);
            remote.writeAndFlush(target);

        } else if (ioResult.IoMsgType() == IOMsgType.WORLD_IO_MSG) {
            IStage stage = stageManager.getStage(StageSession.WORLD_STAGE_KEY);
            stage.writeAndSend(target);
        } else if (ioResult.IoMsgType() == IOMsgType.STAGE_IO_MSG) {

            IStage stage = stageManager.getStage(ioResult.getStageId());
            if (stage != null) {
                stage.writeAndSend(target);
            } else {
                logger.info("场景是空的？ioResult:{}", ioResult);
            }

        } else if (ioResult.IoMsgType() == IOMsgType.TARGET_IO_MSG) {

            for (String roleId : ioResult.getTargets()) {
                Channel remote = channelManager.getChannelByRoleId(roleId);
                if (remote != null && remote.isActive()) {
                    remote.writeAndFlush(target);
                } else {
                    logger.info("这个时候有个Channel关闭了或者未激活。channel:{}", remote);
                }
            }

        } else if (IOMsgType.SHUTDOWN_CHANNEL_MSG == ioResult.IoMsgType() || IOMsgType.NEVER_USE == ioResult.IoMsgType()) {
            String sessionId = ioResult.getSessionId();
            Channel remote = channelManager.getChannelBySessionId(sessionId);
            if (remote != null && remote.isActive()) {
                remote.close();
                /**
                 * 清理到ChannelManager里面的对象，解放内存空间
                 * */
                channelManager.releaseSession(sessionId);
            } else {
                logger.info("已经清理掉了?");
            }
        } else {
            logger.info("怎么做到的??? ioResult:{}", ioResult);
        }

    }

}
