package org.share.cache;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供各种需要保持的信息的容器
 *
 * @Author chen
 * @Date 2018-7-1
 */
public final class CacheManager<T extends ICache> {

    private Map<Object, T> container;

    private CacheManager(boolean sync) {
        if (sync) {
            container = new ConcurrentHashMap<>();
        } else {
            container = new HashMap<>();
        }
    }

    public void add(T entity) {
        container.put(entity.getKey(), entity);
    }

    public T remove(Object key) {
        return container.remove(key);
    }

    public T remove(T entity) {
        return container.remove(entity.getKey());
    }

    public Iterator<Object> getKeyIterator() {
        return container.keySet().iterator();
    }

    public Iterator<T> getValueIterator() {
        return container.values().iterator();
    }

    public T getCache(Object key) {
        return container.get(key);
    }


    public static class Builder{
        public static CacheManager createSyncMapContainer() {
            return new CacheManager(true);
        }

        public static CacheManager createMapContainer() {
            return new CacheManager(false);
        }
    }
}
