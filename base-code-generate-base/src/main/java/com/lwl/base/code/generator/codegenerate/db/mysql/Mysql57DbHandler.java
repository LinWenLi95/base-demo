package com.lwl.base.code.generator.codegenerate.db.mysql;

import com.lwl.base.code.generator.codegenerate.db.DbHandler;
import com.lwl.base.code.generator.codegenerate.db.DbHelper;
import com.lwl.base.code.generator.codegenerate.model.ColumnBean;
import com.lwl.base.code.generator.codegenerate.model.EntityBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Mysql数据库处理器类
 */
public class Mysql57DbHandler implements DbHandler {
    private static final String SHOW_ALL_TABLE_SQL = "show tables";
    private static final String SHOW_TABLE_SQL = "show table status where name='?'";
    private static final String SHOW_FIELD_SQL = "show full fields from ?";
    private DbHelper dbHelper = new DbHelper();

    public Mysql57DbHandler() {
    }

    @Override
    public List<EntityBean> allTables() {
        //1.获取所有表名
        //2.分别根据表名获取表的表信息对象
        List<Object> tables = this.dbHelper.querySingleColumn("show tables", new Object[0]);
        List<EntityBean> entityBeans = new ArrayList<>();
        Iterator var3 = tables.iterator();
        while(var3.hasNext()) {
            Object table = var3.next();
            entityBeans.add(this.byTableName(table.toString()));
        }

        return entityBeans;
    }

    @Override
    public EntityBean byTableName(String tableName) {
        String showTableSql = "show table status where name='?'".replaceAll("\\?", tableName);
        List<Map<String, Object>> tableInfos = this.dbHelper.queryList(showTableSql, new String[]{"Name", "Comment"}, (Object[])null);
        Map<String, Object> tableInfo = (Map<String, Object>) tableInfos.get(0);
        MysqlTable dbTable = new MysqlTable(tableInfo.get("Name").toString(), tableInfo.get("Comment").toString());
        EntityBean entityBean = dbTable.convert();
        String showFieldSql = "show full fields from ?".replaceAll("\\?", tableName);
        List<Map<String, Object>> fields = this.dbHelper.queryList(showFieldSql, new Object[0]);
        List<ColumnBean> columnBeans = new ArrayList<>();
        List<String> imps = new ArrayList<>();

        ColumnBean columnBean;
        for(Iterator var11 = fields.iterator(); var11.hasNext(); columnBeans.add(columnBean)) {
            Map<String, Object> field = (Map)var11.next();
            MysqlColumn dbField = new MysqlColumn(field.get("Field").toString(), field.get("Type").toString(), Boolean.parseBoolean(field.get("Null").toString()), field.get("Comment").toString());
            columnBean = dbField.convert();
            if (!imps.contains(columnBean.getImp())) {
                imps.add(columnBean.getImp());
            }
        }

        entityBean.setImps(imps);
        entityBean.setColumns(columnBeans);
        return entityBean;
    }

    @Override
    public void close() {
        this.dbHelper.closeAll();
    }
}
