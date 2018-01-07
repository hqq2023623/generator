package org.mybatis.generator.extend;

import org.mybatis.generator.api.ShellRunner;

/**
 * @author lzj
 * @date 2018/1/6
 */
public class MyDisconfRun {

    public static void main(String[] args) {

        String config = MyDisconfRun.class.getClassLoader().getResource("generatorConfig-disconf.xml").getFile();
        String[] arg = { "-configfile", config, "-overwrite" };
        ShellRunner.main(arg);


    }


}
