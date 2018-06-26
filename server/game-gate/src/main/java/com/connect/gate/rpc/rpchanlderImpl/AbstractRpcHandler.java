package com.connect.gate.rpc.rpchanlderImpl;

import com.connect.gate.rpc.IRpcHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

public abstract class AbstractRpcHandler implements IRpcHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger("rpc_handler");

    public Logger getLogger() {
        return logger;
    }
}
