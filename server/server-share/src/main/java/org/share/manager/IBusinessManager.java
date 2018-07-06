package org.share.manager;

/**
 * interface IBusinessManager
 * function: 用game-server模块处理用的
 *
 * @Author chens
 * @Date 2018/7/7
 */
public interface IBusinessManager {


    /**
     * 将对象放入RoleId
     */
    void updateRoleIdInfo(String sessionId, String roleId);
}
