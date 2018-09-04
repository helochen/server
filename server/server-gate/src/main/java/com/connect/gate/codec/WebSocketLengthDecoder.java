package com.connect.gate.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * class WebSocketDecode
 * function: 格式是int|int|data
 * 长度，命令，数据
 * 命令后面转换位字符串,数据字节
 *
 * @Author chens
 * @Date 2018/7/10
 */
public class WebSocketLengthDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketLengthDecoder.class);

    /**
     * Decode from one message to an other. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToMessageDecoder} belongs to
     * @param msg the message to decode to an other one
     * @param out the {@link List} to which decoded messages should be added
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) {
        ByteBuf in = msg.content();

        /**
         * byte结构，长度(命令长度+byte的长度)|命令(int)|byte[]
         * */
        if (in.readableBytes() >= 8) {

            int length = in.readInt();
            if (length > 65535) {
                logger.error("消息的长度大于65535!!!! {}", length);
                ctx.close();
                return;
            }
            if (in.readableBytes() < length) {
                logger.error("websocket 理论上不粘包，但是为什么会这样length:{}", length);
                ctx.close();
                return;
            }

            int cmd = in.readInt();/*这就是command命令了*/

            byte[] bytes = new byte[in.readableBytes()];

            in.readBytes(bytes);

            out.add(new Object[]{cmd, bytes});
        }


    }
}
