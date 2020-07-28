package com.youedata.modular.service.impl;

import com.youedata.modular.entity.BookInfo;
import com.youedata.modular.mapper.BookInfoMapper;
import  com.youedata.modular.service.IBookInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 教材管理 服务实现类
 * </p>
 *
 * @author hao.yan
 * @date 2020-07-28
 */
@Service
@Transactional(rollbackFor = Exception.class)

public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements IBookInfoService {

}

