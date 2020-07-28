layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 教材管理管理
     */
    var BookInfo = {
        tableId: "bookInfoTable"
    };

    /**
     * 初始化表格的列
     */
    BookInfo.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: 'center', hide: true, title: ''},
            {field: 'courseName', align: 'center', sort: true, title: '课程名称'},
            {field: 'bookName', align: 'center', sort: true, title: '教材名称'},
            {field: 'bookType', align: 'center', sort: true, title: '教材类型'},
            // {field: 'frontPicUrl', align: 'center', sort: true, title: '教材正面封面URL'},
            // {field: 'backPicUrl', align: 'center', sort: true, title: '教材背面图片URL'},
            {field: 'createTime', align: 'center', sort: true, title: '创建时间'},
            {field: 'updateTime', align: 'center', sort: true, title: '更新时间'},
            {field: 'createUser', align: 'center', sort: true, title: '创建人'},
            {field: 'updateUser', align: 'center', sort: true, title: '修改人'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    BookInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(BookInfo.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    BookInfo.openAddDlg = function () {
        func.open({
            title: '添加教材管理',
            content: Feng.ctxPath + '/bookInfo/add',
            tableId: BookInfo.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    BookInfo.openEditDlg = function (data) {
        func.open({
            title: '修改教材管理',
            content: Feng.ctxPath + '/bookInfo/edit?id=' + data.id,
            tableId: BookInfo.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    BookInfo.exportExcel = function () {
        var checkRows = table.checkStatus(BookInfo.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    BookInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/bookInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(BookInfo.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", data.id);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + BookInfo.tableId,
        url: Feng.ctxPath + '/bookInfo/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: BookInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        BookInfo.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        BookInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        BookInfo.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + BookInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            BookInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            BookInfo.onDeleteItem(data);
        }
    });
});
