package com.connect.gate.rpc;


import io.netty.channel.Channel;

public interface IRpcHandler<T> {

    void active(Channel remote);

    void handler(Channel remote, T data);

    void inactive(Channel remote);

}
