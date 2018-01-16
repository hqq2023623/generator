package org.mybatis.generator.custom.util;

/**
 * generator上下文工具类
 *
 * @author lzj
 * @date 2018/1/15
 */
public class CustomContextUtil {


    private static final String rootPath;

    static {
        rootPath = CustomContextUtil.class.getClassLoader().getResource("").getPath();
    }

    public static String getRootPath() {
        return rootPath;
    }
}
