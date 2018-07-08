package com.connect.gate.rpc;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.AbstractIdleService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public abstract class RpcService extends AbstractIdleService {

    private static Logger LOG = LoggerFactory.getLogger(RpcService.class);

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    private final Class<? extends ServerChannel> channelClass;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private final SocketAddress localAddress;


    public RpcService(final String address, final int port) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Preconditions.checkNotNull(address);

        this.localAddress = new InetSocketAddress(address, port);
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();
        this.channelClass = NioServerSocketChannel.class;

        LOG.info("rpc service init {}:{} elapsed:{}", address, port, stopwatch.stop().toString());
    }

    protected abstract ChannelHandler channelHandler();

    @Override
    protected void startUp() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();

        serverBootstrap.group(bossGroup, workerGroup).channel(this.channelClass)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .localAddress(localAddress)
                .childHandler(channelHandler());

        serverBootstrap.bind().sync();

        LOG.info("rpc service init {}:{} elapsed:{}", localAddress, stopwatch.stop().toString());
    }


    @Override
    protected void shutDown() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();

        // Shut down all event loops to terminate all threads.
        bossGroup.shutdownGracefully();
        // Wait until all threads are terminated.
        bossGroup.terminationFuture().sync();

        // Shut down all event loops to terminate all threads.
        workerGroup.shutdownGracefully();
        // Wait until all threads are terminated.
        workerGroup.terminationFuture().sync();

        LOG.info("rpcServer stop {} elapsed {}", localAddress, stopwatch.stop().toString());
    }
}
