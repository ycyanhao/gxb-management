package com.youedata.generator.generator.common.html;

import com.youedata.generator.generator.base.AbstractCustomGenerator;

import java.io.File;
import java.util.Map;

/**
 * Guns主页面生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class PageIndexGenerator extends AbstractCustomGenerator {

    public PageIndexGenerator(Map<String, Object> tableContext) {
        super(tableContext);
    }

    @Override
    public String getTemplateResourcePath() {
        return "/commonTemplates/page.html.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String lowerEntity = (String) this.tableContext.get("lowerEntity");
        File file = new File(contextParam.getOutputPath() + "/html/" + lowerEntity + "/" + lowerEntity + ".html");
        return file.getAbsolutePath();
    }
}
