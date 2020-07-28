package com.youedata.generator.generator.restful;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.youedata.generator.generator.base.model.ContextParam;
import com.youedata.generator.generator.common.html.PageAddGenerator;
import com.youedata.generator.generator.common.html.PageEditGenerator;
import com.youedata.generator.generator.common.html.PageIndexGenerator;
import com.youedata.generator.generator.common.js.PageAddJsGenerator;
import com.youedata.generator.generator.common.js.PageEditJsGenerator;
import com.youedata.generator.generator.common.js.PageIndexJsGenerator;
import com.youedata.generator.generator.common.sqls.MenuSqlGenerator;
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

            /* 生成前端页面 */
            //生成主页面html
            PageIndexGenerator gunsPageIndexGenerator = new PageIndexGenerator(map);
            gunsPageIndexGenerator.initContext(contextParam);
            gunsPageIndexGenerator.doGeneration();

            //生成主页面js
            PageIndexJsGenerator gunsPageIndexJsGenerator = new PageIndexJsGenerator(map);
            gunsPageIndexJsGenerator.initContext(contextParam);
            gunsPageIndexJsGenerator.doGeneration();

            //生成添加页面html
            PageAddGenerator gunsPageAddGenerator = new PageAddGenerator(map);
            gunsPageAddGenerator.initContext(contextParam);
            gunsPageAddGenerator.doGeneration();

            //生成添加页面的js
            PageAddJsGenerator gunsPageAddJsGenerator = new PageAddJsGenerator(map);
            gunsPageAddJsGenerator.initContext(contextParam);
            gunsPageAddJsGenerator.doGeneration();

            //生成编辑页面html
            PageEditGenerator gunsPageEditGenerator = new PageEditGenerator(map);
            gunsPageEditGenerator.initContext(contextParam);
            gunsPageEditGenerator.doGeneration();

            //生成编辑页面的js
            PageEditJsGenerator gunsPageEditJsGenerator = new PageEditJsGenerator(map);
            gunsPageEditJsGenerator.initContext(contextParam);
            gunsPageEditJsGenerator.doGeneration();

            //生成菜单的sql
            MenuSqlGenerator gunsMenuSqlGenerator = new MenuSqlGenerator(map);
            gunsMenuSqlGenerator.initContext(contextParam);
            gunsMenuSqlGenerator.doGeneration();

        }
    }

}
