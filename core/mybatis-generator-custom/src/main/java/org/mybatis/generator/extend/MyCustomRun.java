package org.mybatis.generator.extend;

import org.mybatis.generator.api.ShellRunner;

/**
 * @author lzj
 * @date 2018/1/6
 */
public class MyCustomRun {

    public static void main(String[] args) {

        String config = MyCustomRun.class.getClassLoader().getResource("generatorConfig-custom.xml").getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);


    }


}
