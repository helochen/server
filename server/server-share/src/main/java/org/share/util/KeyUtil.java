package org.share.util;

import java.util.UUID;

/**
 * class KeyUtil
 * function: 用于生成唯一的key值
 *
 * @Author chens
 * @Date 2018/7/1
 */
public class KeyUtil {

    public static String stringKey() {
        return UUID.randomUUID().toString();
    }


}
