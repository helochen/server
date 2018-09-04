package com.connect.gate;

import com.connect.gate.codec.WebSocketLengthDecoder;
import com.connect.gate.codec.WebSocketLengthEncoder;
import com.connect.gate.handler.GameServerHandler;
import com.connect.gate.rpc.IRpcHandler;
import com.connect.gate.rpc.RpcService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateServer extends RpcService {

    private static final Logger LOG = LoggerFactory.getLogger(GateServer.class);

    private final int idleTime;
    private final String address;
    private final int port;

    private final IRpcHandler<Object> handler;


    public GateServer(String address, int port, final int idleTime, final IRpcHandler<Object> handler) {
        super(address, port);
        this.address = address;
        this.port = port;
        this.handler = handler;
        this.idleTime = idleTime;
    }

    protected ChannelHandler channelHandler() {

        return new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline channelPipeline = ch.pipeline();

                channelPipeline.addLast(new HttpServerCodec())
                        .addLast(new HttpObjectAggregator(65535))
                        .addLast(new ChunkedWriteHandler())
                        .addLast(new WebSocketServerProtocolHandler("/", "", false))
                        .addLast(new WebSocketLengthDecoder())
                        .addLast(new WebSocketLengthEncoder())
                        .addLast(new GameServerHandler(handler));
            }
        };
    }
}
