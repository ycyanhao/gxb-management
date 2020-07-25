package com.youedata.generator.generator.base;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.youedata.generator.engine.MpVelocityTemplateEngine;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器规范
 *
 * @author hao.yan
 */
@Slf4j
public abstract class AbstractMpGenerator extends Generator {

    /**
     * mybatis-plus代码生成器配置
     */
    protected GlobalConfig globalConfig = new GlobalConfig();

    protected DataSourceConfig dataSourceConfig = new DataSourceConfig();

    protected StrategyConfig strategyConfig = new StrategyConfig();

    protected PackageConfig packageConfig = new PackageConfig();

    protected TemplateConfig templateConfig = new TemplateConfig();

    protected InjectionConfig injectionConfig = new InjectionConfig() {
        @Override
        public void initMap() {
            Map<String, String> packageInfo = this.getConfig().getPackageInfo();
            if (this.getMap() != null && this.getMap().size() > 0) {
                for (Map.Entry<String, Object> entry : this.getMap().entrySet()) {
                    packageInfo.put(entry.getKey(), (String) entry.getValue());
                }
            }
        }
    };

    /**
     * mybatis plus生成之后可以获取到他的table元数据，加以利用
     */
    private List<TableInfo> tableInfos = null;
    private Map<String, Map<String, Object>> everyTableContexts = new HashMap<>();

    /**
     * 执行mybatis-plus的代码生成
     *
     * @author hao.yan
     */
    @Override
    public void doGeneration() {

        beforeGeneration();

        AutoGenerator autoGenerator = new AutoGenerator();

        //使用重写版的mp代码生成器
        autoGenerator.setTemplateEngine(new MpVelocityTemplateEngine());

        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.execute();

        //获取table信息,用于其他代码生成
        tableInfos = autoGenerator.getConfig().getTableInfoList();

        //获取mp代码生成时候的所有变量
        for (TableInfo tableInfo : tableInfos) {
            Map<String, Object> tableContextMap = autoGenerator.getTemplateEngine().getObjectMap(tableInfo);
            everyTableContexts.put(tableInfo.getName(), tableContextMap);
        }

        afterGeneration();
    }

    public List<TableInfo> getTableInfos() {
        return tableInfos;
    }

    public Map<String, Map<String, Object>> getEveryTableContexts() {
        return everyTableContexts;
    }
}
