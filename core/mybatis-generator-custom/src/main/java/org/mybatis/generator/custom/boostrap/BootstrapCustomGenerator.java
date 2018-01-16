package org.mybatis.generator.custom.boostrap;

import org.mybatis.generator.custom.api.CustomShellRunner;

import java.net.URL;

/**
 * @author lzj
 * @date 2018/1/15
 */
public class BootstrapCustomGenerator {

    public static void main(String[] args) throws Exception {
        URL configFileUrl = BootstrapCustomGenerator.class.getClassLoader().getResource("generatorConfig-custom.xml");
        String config = configFileUrl.getFile();
        String[] arg = {"-configfile", config, "-overwrite", "-verbose"};
        CustomShellRunner.main(arg);

    }


}