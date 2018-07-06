package org.stage.manager.impl;

import org.share.cache.CacheManager;
import org.stage.IStage;
import org.stage.session.StageSession;

/**
 * class StageManager
 * function:场景管理器，负责管理所有的用户场景的
 * StageSession实现了ICache接口，可以用CacheManager管理场景集合
 *
 * @Author chens
 * @Date 2018/7/1
 */
public class StageCacheManager {

    private final CacheManager<StageSession> cacheManager = CacheManager.Builder.createSyncMapContainer();

    /**
     * TODO
     * TODO
     * TODO
     * TODO
     * TODO
     */

    /**
     * 基本的返回一个场景对象咯
     * @param stageId
     * @return
     */
    public IStage getStage(String stageId) {
        return cacheManager.getCache(stageId);
    }

    /**
     * 删除一个场景对象，
     * 删除的时候需要告知所有的内在用户退出该场景
     * @param stageId
     * @return
     */
    public IStage removeStage(String stageId) {
        IStage stage = cacheManager.remove(stageId);
        /**
         * TODO 场景被释放的时候，需要给所有的管理的channel推送相关消息以及操作
         *
         * 有没有StageSession只是场景的一个组成对象，如果场景被释放
         * 根据Stage的具体实现方法去操作用户
         * */


        return stage;
    }
}