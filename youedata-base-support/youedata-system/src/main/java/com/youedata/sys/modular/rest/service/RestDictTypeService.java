package com.youedata.sys.modular.rest.service;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youedata.base.enums.CommonStatus;
import com.youedata.base.pojo.page.LayuiPageFactory;
import com.youedata.base.pojo.page.LayuiPageInfo;
import com.youedata.sys.core.exception.enums.BizExceptionEnum;
import com.youedata.sys.modular.rest.entity.RestDict;
import com.youedata.sys.modular.rest.entity.RestDictType;
import com.youedata.sys.modular.rest.mapper.RestDictTypeMapper;
import com.youedata.sys.modular.system.model.params.DictTypeParam;
import com.youedata.sys.modular.system.model.result.DictTypeResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
@Service
public class RestDictTypeService extends ServiceImpl<RestDictTypeMapper, RestDictType> {

    @Resource
    private RestDictService restDictService;

    /**
     * 新增
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public void add(DictTypeParam param) {

        //判断是否已经存在同编码或同名称字典
        QueryWrapper<RestDictType> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("code", param.getCode()).or().eq("name", param.getName());
        List<RestDictType> list = this.list(dictQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }

        RestDictType entity = getEntity(param);

        //设置状态
        entity.setStatus(CommonStatus.ENABLE.getCode());

        this.save(entity);
    }

    /**
     * 删除
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(DictTypeParam param) {

        if (param == null || param.getDictTypeId() == null) {
            throw new RequestEmptyException("字典类型id为空");
        }

        //删除字典类型
        this.removeById(getKey(param));

        //删除字典
        this.restDictService.remove(new QueryWrapper<RestDict>().eq("dict_type_id", getKey(param)));
    }

    /**
     * 更新
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public void update(DictTypeParam param) {
        RestDictType oldEntity = getOldEntity(param);
        RestDictType newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);

        //判断编码是否重复
        QueryWrapper<RestDictType> wrapper = new QueryWrapper<RestDictType>()
                .and(i -> i.eq("code", newEntity.getCode()).or().eq("name", newEntity.getName()))
                .and(i -> i.ne("dict_type_id", newEntity.getDictTypeId()));
        int dicts = this.count(wrapper);
        if (dicts > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }

        this.updateById(newEntity);
    }

    /**
     * 查询单条数据，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public DictTypeResult findBySpec(DictTypeParam param) {
        return null;
    }

    /**
     * 查询列表，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public List<DictTypeResult> findListBySpec(DictTypeParam param) {
        return null;
    }

    /**
     * 查询分页数据，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public LayuiPageInfo findPageBySpec(DictTypeParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<RestDictType> objectQueryWrapper = new QueryWrapper<>();
        if (ToolUtil.isNotEmpty(param.getCondition())) {
            objectQueryWrapper.and(i -> i.eq("code", param.getCondition()).or().eq("name", param.getCondition()));
        }
        if (ToolUtil.isNotEmpty(param.getStatus())) {
            objectQueryWrapper.and(i -> i.eq("status", param.getStatus()));
        }
        if (ToolUtil.isNotEmpty(param.getSystemFlag())) {
            objectQueryWrapper.and(i -> i.eq("system_flag", param.getSystemFlag()));
        }

        pageContext.setAsc("sort");

        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(DictTypeParam param) {
        return param.getDictTypeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private RestDictType getOldEntity(DictTypeParam param) {
        return this.getById(getKey(param));
    }

    private RestDictType getEntity(DictTypeParam param) {
        RestDictType entity = new RestDictType();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
