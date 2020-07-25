package com.youedata.generator.generator.restful;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.youedata.generator.generator.base.model.ContextParam;
import com.youedata.generator.generator.restful.controller.RestfulControllerGenerator;
import com.youedata.generator.generator.restful.feign.FeignApiGenerator;
import com.youedata.generator.generator.restful.feign.FeignProviderGenerator;
import com.youedata.generator.generator.restful.mybatisplus.DefaultMpGenerator;
import com.youedata.generator.generator.restful.mybatisplus.param.MpParam;

import java.util.List;
import java.util.Map;

/**
 * RestFul风格代码生成的执行器
 *
 * @author hao.yan
 */
public class RestfulApiExecutor {

    public static void executor(ContextParam contextParam, MpParam mpContext) {
        //执行mp的代码生成，生成entity,dao,service,model，生成后保留数据库元数据
        DefaultMpGenerator defaultMpGenerator = new DefaultMpGenerator(mpContext);
        defaultMpGenerator.initContext(contextParam);
        defaultMpGenerator.doGeneration();

        //获取元数据
        List<TableInfo> tableInfos = defaultMpGenerator.getTableInfos();
        Map<String, Map<String, Object>> everyTableContexts = defaultMpGenerator.getEveryTableContexts();

        //遍历所有表
        for (TableInfo tableInfo : tableInfos) {
            Map<String, Object> map = everyTableContexts.get(tableInfo.getName());

            //生成控制器
            RestfulControllerGenerator restfulControllerGenerator = new RestfulControllerGenerator(map);
            restfulControllerGenerator.initContext(contextParam);
            restfulControllerGenerator.doGeneration();
        }
    }

}
