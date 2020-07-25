package com.youedata.sys.modular.rest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youedata.base.pojo.node.ZTreeNode;
import com.youedata.sys.modular.rest.entity.RestDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基础字典 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
public interface RestDictMapper extends BaseMapper<RestDict> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> dictTree(@Param("dictTypeId") Long dictTypeId);

    /**
     * where parentIds like ''
     */
    List<RestDict> likeParentIds(@Param("dictId") Long dictId);
}
