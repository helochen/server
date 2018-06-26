package com.exchange.pool.factory.business.executor;

import com.google.common.util.concurrent.ListenableFuture;
import org.share.msg.IOResult;

public interface IServiceExecuor {

    ListenableFuture<IOResult> execute(String group, Runnable runnable);
}
