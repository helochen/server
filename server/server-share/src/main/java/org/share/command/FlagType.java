package org.share.command;

/**
 * interface FlagType
 * function: 放在业务上注释用的
 *
 * @Author chens
 * @Date 2018/7/6
 */
public interface FlagType {

    /**
     * 初始化的标识
     * */
    byte ACTIVE_INIT = 1;

    /**
     * 已经登陆成功标识
     */
    byte LOGIN_SUCCESS = 1 << 1;

    /**
     * 放弃链接标识
     */
    byte CLOSE_LOGIN = 1 << 2;

}
