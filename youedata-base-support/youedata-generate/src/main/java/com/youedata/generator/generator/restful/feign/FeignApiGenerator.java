package com.youedata.generator.generator.restful.feign;

import com.youedata.generator.generator.base.AbstractCustomGenerator;
import org.beetl.core.Template;

import java.io.File;
import java.util.Map;

/**
 * feign的api生成器
 *
 * @author fengshuonan
 * @date 2018-12-13-2:20 PM
 */
public class FeignApiGenerator extends AbstractCustomGenerator {

    public FeignApiGenerator(Map<String, Object> tableContext) {
        super(tableContext);
    }

    @Override
    public void bindingOthers(Template template) {
        template.binding("apiPackage", contextParam.getProPackage() + ".api");
    }

    @Override
    public String getTemplateResourcePath() {
        return "/feignTemplates/FeignApi.btl";
    }

    @Override
    public String getGenerateFilePath() {
        String proPackage = this.contextParam.getProPackage();
        String proPath = proPackage.replaceAll("\\.", "/");
        File file = new File(contextParam.getOutputPath() + "/" + proPath + "/api/" + tableContext.get("entity") + "Api.java");
        return file.getAbsolutePath();
    }
}
