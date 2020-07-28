package com.youedata.modular.controller;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youedata.base.pojo.page.LayuiPageFactory;
import com.youedata.base.tips.DCResponse;
import com.youedata.modular.entity.BookInfo;
import com.youedata.modular.model.dto.BookInfoDTO;
import com.youedata.modular.service.IBookInfoService;
import com.youedata.sys.core.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 教材管理控制器
 *
 * @author hao.yan
 * @date 2020-07-28 16:31:02
 */
@Controller
@Api(value = "/bookInfo", tags = "教材管理管理")
@RequestMapping("/bookInfo")
public class BookInfoController {

    private String PREFIX = "/bookInfo";

    @Autowired
    private IBookInfoService bookInfoService;

    /**
     * 新增
     *
     * @author hao.yan
     * @date 2020-07-28
     */
    @ResponseBody
    @PostMapping("/addItem")
    @ApiOperation(value = "新增")
    public DCResponse<Void> addItem(BookInfoDTO bookInfoDto) {
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(bookInfoDto, bookInfo);
        bookInfo.setCreateUser(UserHolder.getUserId());
        bookInfo.setCreateTime(LocalDateTime.now());
        this.bookInfoService.save(bookInfo);
        return DCResponse.ok(null);
    }

    /**
     * 根据主键删除
     *
     * @author hao.yan
     * @date 2020-07-28
     */
    @ResponseBody
    @PostMapping("/delete")
    @ApiOperation("删除")
    public DCResponse<Void> delete(@RequestParam("id") String id) {
        this.bookInfoService.removeById(id);
        return DCResponse.ok(null);
    }

    /**
     * 修改
     *
     * @author hao.yan
     * @date 2020-07-28
     */
    @ResponseBody
    @PostMapping("/editItem")
    @ApiOperation("根据ID修改信息")
    public DCResponse<Void> editItem(BookInfoDTO bookInfoDto) {
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(bookInfoDto, bookInfo);
        bookInfo.setUpdateUser(UserHolder.getUserId());
        bookInfo.setUpdateTime(LocalDateTime.now());
        this.bookInfoService.updateById(bookInfo);
        return DCResponse.ok(null);
    }

    /**
     * 根据主键查询单条详情
     *
     * @author hao.yan
     * @date 2020-07-28
     */
    @ResponseBody
    @PostMapping("/detail")
    @ApiOperation(value = "根据主键查询单条详情")
    public ResponseData detail(@RequestParam("id") String id) {
        return ResponseData.success(this.bookInfoService.getById(id));
    }

    /**
     * 分页查询列表
     *
     * @author hao.yan
     * @date 2020-07-28
     */
    @ResponseBody
    @GetMapping("/list")
    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "当前页码", required = true, defaultValue = "1", paramType = "query", dataType = "int", example = "1"),
        @ApiImplicitParam(name = "limit", value = "每页多少条数据", required = true, defaultValue = "10", paramType = "query", dataType = "int", example = "20")
    })
    public Object list(BookInfoDTO bookInfoDto) {
        BookInfo bookInfo = new BookInfo();
        BeanUtils.copyProperties(bookInfoDto, bookInfo);

        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();

        //根据条件查询
        page.setRecords(this.bookInfoService.page(page, Wrappers.query(bookInfo)).getRecords());

        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 跳转到主页面
     *
     * @author hao.yan
     * @Date 2020-07-28
     */
    @RequestMapping()
    public String index() {
        return PREFIX + "/bookInfo.html";
    }

    /**
     * 新增页面
     *
     * @author hao.yan
     * @Date 2020-07-28
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/bookInfo_add.html";
    }

    /**
     * 编辑页面
     *
     * @author hao.yan
     * @Date 2020-07-28
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/bookInfo_edit.html";
    }

}
