package com.youedata.generator.generator.base;

import com.youedata.generator.generator.base.model.ContextParam;

/**
 * 代码生成器
 *
 * @author hao.yan
 */
public abstract class Generator {

    protected ContextParam contextParam;

    /**
     * 初始化配置
     *
     * @author hao.yan
     */
    public void initContext(ContextParam paramContext) {
        this.contextParam = paramContext;
    }

    /**
     * 代码生成之前，自由发挥
     *
     * @author hao.yan
     */
    protected void beforeGeneration() {

    }

    /**
     * 执行代码生成
     *
     * @author hao.yan
     */
    public abstract void doGeneration();

    /**
     * 代码生成之后，自由发挥
     *
     * @author hao.yan
     */
    void afterGeneration() {
    }

}
