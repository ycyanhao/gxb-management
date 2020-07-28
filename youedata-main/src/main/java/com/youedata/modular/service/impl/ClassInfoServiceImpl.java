package com.youedata.modular.service.impl;

import com.youedata.modular.entity.ClassInfo;
import com.youedata.modular.mapper.ClassInfoMapper;
import  com.youedata.modular.service.IClassInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 班级信息 服务实现类
 * </p>
 *
 * @author hao.yan
 * @date 2020-07-28
 */
@Service
@Transactional(rollbackFor = Exception.class)

public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoMapper, ClassInfo> implements IClassInfoService {

}

