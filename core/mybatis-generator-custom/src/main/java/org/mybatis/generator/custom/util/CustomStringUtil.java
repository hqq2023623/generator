package org.mybatis.generator.custom.util;

/**
 * @author lzj
 * @date 2018/1/15
 */
public class CustomStringUtil {


    public static boolean isEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        if (str.replace(" ", "").isEmpty()) {
            return true;
        }
        return false;
    }


}
