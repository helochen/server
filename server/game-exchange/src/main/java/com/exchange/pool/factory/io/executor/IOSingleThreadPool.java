package com.exchange.pool.factory.io.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 给IO使用个线程池，业务线程完成后回调给guava的线程池
 * Singleton
 *
 * @Author chen
 * @Date 2028.6.25
 */
public class IOSingleThreadPool implements IIOExecutor {

    private static final Logger logger = LoggerFactory.getLogger(IOSingleThreadPool.class);

    private int threadSize;

    private int queues;

    private Executor executor;

    public IOSingleThreadPool(int threadSize, int queueSize) {
        this.threadSize = threadSize;
        this.queues = queueSize;
    }

    @Override
    public Executor getExecutor() {
        if (executor == null) {
            synchronized (this) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(threadSize, threadSize,
                            0L, TimeUnit.MILLISECONDS
                            , queues == 0 ? new SynchronousQueue<>()
                            : queues < 0 ? new LinkedBlockingDeque<>()
                            : new LinkedBlockingDeque<>(queues),
                            /*拒绝策略*/
                            (r, executor) -> {
                                if (!executor.isShutdown()) {
                                    try {
                                        /*抛弃超过2秒的队列头，加入队列*/
                                        executor.getQueue().poll(2000, TimeUnit.MILLISECONDS);
                                        executor.execute(r);
                                    } catch (InterruptedException e) {
                                        /*打印异常不做任何处理*/
                                        e.printStackTrace();
                                        logger.error("{} , rejected thread :{} , exception:{}", IOSingleThreadPool.class, r, e);
                                    }
                                }
                    });
                }
            }
        }
        return executor;
    }


}
