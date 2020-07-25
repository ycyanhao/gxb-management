package com.youedata.generator.generator.restful.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.youedata.generator.generator.base.AbstractMpGenerator;
import com.youedata.generator.generator.restful.mybatisplus.param.MpParam;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 默认的mybatis-plus生成器
 *
 * @author hao.yan
 */
public class DefaultMpGenerator extends AbstractMpGenerator {

    private MpParam mpContextParam;

    public DefaultMpGenerator(MpParam mpContextParam) {
        this.mpContextParam = mpContextParam;
    }

    /**
     * 执行mybatis-plus的代码生成
     *
     * @author hao.yan
     */
    @Override
    public void beforeGeneration() {

        // 全局配置
        globalConfig.setOutputDir(contextParam.getOutputPath());
        globalConfig.setFileOverride(false);
        globalConfig.setActiveRecord(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setEnableCache(false);
        globalConfig.setOpen(false);
        globalConfig.setAuthor(contextParam.getAuthor());
        globalConfig.setIdType(IdType.UUID);

        globalConfig.setServiceName("I%sService");
        globalConfig.setServiceImplName("%sServiceImpl");

        // 数据源配置
        if (contextParam.getJdbcUrl().contains("oracle")) {
            dataSourceConfig.setDbType(DbType.ORACLE);
        } else if (contextParam.getJdbcUrl().contains("postgresql")) {
            dataSourceConfig.setDbType(DbType.POSTGRE_SQL);
        } else if (contextParam.getJdbcUrl().contains("sqlserver")) {
            dataSourceConfig.setDbType(DbType.SQL_SERVER);
        } else if (contextParam.getJdbcUrl().contains("mysql")) {
            dataSourceConfig.setDbType(DbType.MYSQL);
        }
        dataSourceConfig.setDriverName(contextParam.getJdbcDriver());
        dataSourceConfig.setUrl(contextParam.getJdbcUrl());
        dataSourceConfig.setUsername(contextParam.getJdbcUserName());
        dataSourceConfig.setPassword(contextParam.getJdbcPassword());

        // 策略配置
        // 大写命名
        strategyConfig.setCapitalMode(false);

        // 此处可以移除表前缀表前缀
        strategyConfig.setTablePrefix(this.mpContextParam.getRemoveTablePrefix());

        // 表名生成策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityTableFieldAnnotationEnable(true);

        // 需要生成的表
        strategyConfig.setInclude(this.mpContextParam.getIncludeTables());

        // 公共字段填充
//        ArrayList<TableFill> tableFills = new ArrayList<>();
//        tableFills.add(new TableFill("CREATE_TIME", FieldFill.INSERT));
//        tableFills.add(new TableFill("UPDATE_TIME", FieldFill.UPDATE));
//        tableFills.add(new TableFill("CREATE_USER", FieldFill.INSERT));
//        tableFills.add(new TableFill("UPDATE_USER", FieldFill.UPDATE));
//        strategyConfig.setTableFillList(tableFills);

        // 自定义模板配置
        templateConfig.setController(null);
        templateConfig.setXml("/mpTemplates/mapper.xml.vm");
        templateConfig.setMapper("/mpTemplates/mapper.java.vm");

        templateConfig.setService("/mpTemplates/service.java.vm");
        templateConfig.setServiceImpl("/mpTemplates/serviceImpl.java.vm");

        // 包配置
        packageConfig.setParent(this.contextParam.getProPackage());
        packageConfig.setModuleName("");
        packageConfig.setXml("mapper.mapping");

        packageConfig.setServiceImpl("service.impl");
        packageConfig.setService("service");

        //自定义specification model的生成
        String specParamsTemplatePath = "/mpTemplates/specDTOSwagger.java.vm";

        List<FileOutConfig> focList = new ArrayList<>();

        String paramsParentPackage = this.contextParam.getProPackage().replaceAll("\\.", "/") + "/model/dto";
        File dtoDir = new File(contextParam.getOutputPath() + "/" + paramsParentPackage);
        if (!dtoDir.exists()) {
            dtoDir.mkdirs();
        }

        //model的查询条件
        focList.add(new FileOutConfig(specParamsTemplatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return globalConfig.getOutputDir() + "/" + paramsParentPackage + "/" + tableInfo.getEntityName() + "DTO.java";
            }
        });

        injectionConfig.setFileOutConfigList(focList);

        //自定义specification里的参数
        HashMap<String, Object> contexMap = new HashMap<>();
        contexMap.put("EntitySpecParams", this.contextParam.getProPackage() + ".model.dto");
        contexMap.put("EntitySpecResult", this.contextParam.getProPackage() + ".model.vo");
        injectionConfig.setMap(contexMap);
    }

}
