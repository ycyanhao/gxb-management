layui.use(['table', 'admin', 'ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 班级信息管理
     */
    var ClassInfo = {
        tableId: "classInfoTable"
    };

    /**
     * 初始化表格的列
     */
    ClassInfo.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'id', align: 'center', hide: true, title: '班级主键'},
            {field: 'classNo', align: 'center', sort: true, title: '班级编号'},
            {field: 'courseName', align: 'center', sort: true, title: '课程名称'},
            {field: 'trainLevel', align: 'center', sort: true, title: '培训等级'},
            {field: 'trainForm', align: 'center', sort: true, title: '培训形式'},
            {field: 'trainAddress', align: 'center', sort: true, title: '培训地点'},
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
    ClassInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ClassInfo.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    ClassInfo.openAddDlg = function () {
        func.open({
            title: '添加班级信息',
            content: Feng.ctxPath + '/classInfo/add',
            tableId: ClassInfo.tableId
        });
    };

    /**
    * 点击编辑
    *
    * @param data 点击按钮时候的行数据
    */
    ClassInfo.openEditDlg = function (data) {
        func.open({
            title: '修改班级信息',
            content: Feng.ctxPath + '/classInfo/edit?id=' + data.id,
            tableId: ClassInfo.tableId
        });
    };

    /**
     * 导出excel按钮
     */
    ClassInfo.exportExcel = function () {
        var checkRows = table.checkStatus(ClassInfo.tableId);
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
    ClassInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/classInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ClassInfo.tableId);
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
        elem: '#' + ClassInfo.tableId,
        url: Feng.ctxPath + '/classInfo/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: ClassInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ClassInfo.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ClassInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ClassInfo.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + ClassInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ClassInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ClassInfo.onDeleteItem(data);
        }
    });
});
