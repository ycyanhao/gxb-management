package com.youedata.generator.generator.common;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.youedata.generator.generator.base.model.ContextParam;
import com.youedata.generator.generator.common.controller.ControllerGenerator;
import com.youedata.generator.generator.common.html.PageAddGenerator;
import com.youedata.generator.generator.common.html.PageEditGenerator;
import com.youedata.generator.generator.common.html.PageIndexGenerator;
import com.youedata.generator.generator.common.js.PageAddJsGenerator;
import com.youedata.generator.generator.common.js.PageEditJsGenerator;
import com.youedata.generator.generator.common.js.PageIndexJsGenerator;
import com.youedata.generator.generator.common.mybatisplus.MpGenerator;
import com.youedata.generator.generator.common.sqls.MenuSqlGenerator;
import com.youedata.generator.generator.restful.mybatisplus.param.MpParam;

import java.util.List;
import java.util.Map;

public class Executor {

    /**
     * 生成器
     *
     * @author hao.yan
     */
    public static void executor(ContextParam contextParam, MpParam mpContext) {

        //生成entity,dao,service,model
        MpGenerator mpGenerator = new MpGenerator(mpContext);
        mpGenerator.initContext(contextParam);
        mpGenerator.doGeneration();

        //获取元数据
        List<TableInfo> tableInfos = mpGenerator.getTableInfos();
        Map<String, Map<String, Object>> everyTableContexts = mpGenerator.getEveryTableContexts();

        //遍历所有表
        for (TableInfo tableInfo : tableInfos) {
            Map<String, Object> map = everyTableContexts.get(tableInfo.getName());

            //生成控制器
            ControllerGenerator gunsControllerGenerator = new ControllerGenerator(map);
            gunsControllerGenerator.initContext(contextParam);
            gunsControllerGenerator.doGeneration();

//            //生成主页面html
//            PageIndexGenerator gunsPageIndexGenerator = new PageIndexGenerator(map);
//            gunsPageIndexGenerator.initContext(contextParam);
//            gunsPageIndexGenerator.doGeneration();
//
//            //生成主页面js
//            PageIndexJsGenerator gunsPageIndexJsGenerator = new PageIndexJsGenerator(map);
//            gunsPageIndexJsGenerator.initContext(contextParam);
//            gunsPageIndexJsGenerator.doGeneration();
//
//            //生成添加页面html
//            PageAddGenerator gunsPageAddGenerator = new PageAddGenerator(map);
//            gunsPageAddGenerator.initContext(contextParam);
//            gunsPageAddGenerator.doGeneration();
//
//            //生成添加页面的js
//            PageAddJsGenerator gunsPageAddJsGenerator = new PageAddJsGenerator(map);
//            gunsPageAddJsGenerator.initContext(contextParam);
//            gunsPageAddJsGenerator.doGeneration();
//
//            //生成编辑页面html
//            PageEditGenerator gunsPageEditGenerator = new PageEditGenerator(map);
//            gunsPageEditGenerator.initContext(contextParam);
//            gunsPageEditGenerator.doGeneration();
//
//            //生成编辑页面的js
//            PageEditJsGenerator gunsPageEditJsGenerator = new PageEditJsGenerator(map);
//            gunsPageEditJsGenerator.initContext(contextParam);
//            gunsPageEditJsGenerator.doGeneration();

            //生成菜单的sql
            MenuSqlGenerator gunsMenuSqlGenerator = new MenuSqlGenerator(map);
            gunsMenuSqlGenerator.initContext(contextParam);
            gunsMenuSqlGenerator.doGeneration();
        }
    }

}
