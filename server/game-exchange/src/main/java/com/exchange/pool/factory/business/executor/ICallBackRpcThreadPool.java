package com.exchange.pool.factory.business.executor;

import com.google.common.util.concurrent.ListenableFuture;

public interface ICallBackRpcThreadPool {

    ListenableFuture execute(Runnable runnable);
}
