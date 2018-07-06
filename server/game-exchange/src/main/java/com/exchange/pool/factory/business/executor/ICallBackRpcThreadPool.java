package com.exchange.pool.factory.business.executor;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;

public interface ICallBackRpcThreadPool {

    ListenableFuture execute(Callable runnable);
}
