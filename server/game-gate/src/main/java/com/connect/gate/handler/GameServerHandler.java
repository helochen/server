package com.connect.gate.handler;

import com.connect.gate.rpc.IRpcHandler;
import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class GameServerHandler extends SimpleChannelInboundHandler<Object>{

    private static final Logger log = LoggerFactory.getLogger("RPC_HANDLER");

    private final IRpcHandler<Object> rpcHandler;


    public GameServerHandler(IRpcHandler<Object> rpcHandler) {
        Preconditions.checkNotNull(rpcHandler);
        this.rpcHandler = rpcHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            rpcHandler.handler(ctx.channel(), msg);
            ((WebSocketFrame) msg).retain();
        }/* else {
            ctx.channel().close();
        }*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        rpcHandler.active(ctx.channel());
        log.debug("channel :{} active............" , ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        rpcHandler.inactive(ctx.channel());
        log.debug("channel :{} inactive..............", ctx.channel());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("channel:{} throw an exception; expMsg:{}", ctx.channel(), cause.getMessage());
        if (ctx.channel().isActive()) {
            ctx.channel().close();
        }
    }

    /**
     * Calls {@link ChannelHandlerContext#fireUserEventTriggered(Object)} to forward
     * to the next {ChannelInboundHandler} in the {ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
