package org.stage.io;

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

        if (ioResult.IoMsgType() == IOMsgType.SELF_IO_MSG) {

            String sessionId = ioResult.getSessionId();
            Channel remote = channelManager.getChannelBySessionId(sessionId);
            remote.writeAndFlush(ioResult.getSource());

        } else if (ioResult.IoMsgType() == IOMsgType.WORLD_IO_MSG) {
            IStage stage = stageManager.getStage(StageSession.WORLD_STAGE_KEY);
            if (stage != null && ioResult.getSource() instanceof byte[]) {
                stage.writeAndSend((byte[]) ioResult.getSource());
            } else {
                logger.info("命令不是不是字节数组吗？stage status :{}", stage);
            }
        } else if (ioResult.IoMsgType() == IOMsgType.STAGE_IO_MSG) {

            IStage stage = stageManager.getStage(ioResult.getStageId());
            if (stage != null && ioResult.getSource() instanceof byte[]) {
                stage.writeAndSend((byte[]) ioResult.getSource());
            } else {
                logger.info("命令不是不是字节数组吗？stage status :{}", stage);
            }

        } else if (ioResult.IoMsgType() == IOMsgType.TARGET_IO_MSG) {

            for (String roleId : ioResult.getTargets()) {
                Channel remote = channelManager.getChannelByRoleId(roleId);
                if (remote != null && remote.isActive()) {
                    remote.writeAndFlush(ioResult.getSource());
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
