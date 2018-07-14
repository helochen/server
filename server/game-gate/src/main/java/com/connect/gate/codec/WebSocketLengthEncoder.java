package com.connect.gate.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * class WebSocketLengthEncoder
 * function:
 *
 * @Author chens
 * @Date 2018/7/10
 */
public class WebSocketLengthEncoder extends MessageToByteEncoder<ByteBuf> {
    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        if (msg != null) {
            int length = msg.readableBytes();

            if (length > 1000) {

                int readIndex = 0;

                int lastLength = length;

                while (lastLength > 1000) {
                    out.writeBytes(msg, readIndex, 1000);

                    readIndex += 1000;
                    lastLength -= 1000;

                    ctx.channel().writeAndFlush(new BinaryWebSocketFrame(out.retain()));
                    out.clear();
                }

                if (lastLength > 0) {

                    out.writeBytes(msg, 0, lastLength);
                    ctx.channel().writeAndFlush(new BinaryWebSocketFrame(out.retain()));

                }
            } else {
                out.writeBytes(msg);
                ctx.channel().writeAndFlush(out.retain());
            }
        }
    }
}
