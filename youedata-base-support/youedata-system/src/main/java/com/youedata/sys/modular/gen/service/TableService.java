package com.youedata.sys.modular.gen.service;

import cn.stylefeng.roses.core.util.HttpContext;
import com.youedata.base.db.entity.DatabaseInfo;
import com.youedata.base.db.util.DbUtil;
import com.youedata.sys.modular.db.mapper.DatabaseInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.youedata.sys.modular.db.controller.DatabaseInfoController.CONDITION_FIELDS;


/**
 * 数据库表服务
 *
 * @author fengshuonan
 * @date 2019-05-06-19:04
 */
@Service
public class TableService {

    @Autowired
    private DatabaseInfoMapper databaseInfoMapper;

    public List<Map<String, Object>> getTableFields(Long dbId, String tableName) {

        //查找数据库元数据信息
        DatabaseInfo databaseInfo = databaseInfoMapper.selectById(dbId);

        //获取对应表的所有字段
        List<Map<String, Object>> tableFields = DbUtil.getTableFields(databaseInfo, tableName);

        //查询session中有无已经选中的数据，若有以选中的字段，则增加LAY_CHECKED标识
        HttpSession session = HttpContext.getRequest().getSession();
        Map<String, String[]> fieldMap = (Map<String, String[]>) session.getAttribute(CONDITION_FIELDS);
        if (fieldMap != null) {
            String[] strings = fieldMap.get(tableName);
            if (strings != null) {
                for (String fieldName : strings) {
                    for (Map<String, Object> tableField : tableFields) {
                        if (fieldName.equals(tableField.get("columnName"))) {
                            tableField.put("LAY_CHECKED", true);
                        }
                    }
                }
            }
        }

        return tableFields;
    }

}
