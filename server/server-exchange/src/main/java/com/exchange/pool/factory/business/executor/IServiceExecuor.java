package com.exchange.pool.factory.business.executor;

import com.google.common.util.concurrent.ListenableFuture;
import org.share.msg.IOResult;

import java.util.concurrent.Callable;

public interface IServiceExecuor {

    ListenableFuture<IOResult> execute(String group, Callable runnable);
}
