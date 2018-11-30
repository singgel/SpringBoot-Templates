package com.hks.hbase.model;

import java.util.ArrayList;
import java.util.List;

import static com.hks.hbase.util.CheckUtil.checkIsEmpty;

public abstract class HBaseTable {
    private String tableName;
    protected List<String> columnFamily = new ArrayList<>();

    public HBaseTable(String tableName){
        checkIsEmpty(tableName,"表名不能为空");
        this.tableName = tableName;
        setColumnFamily();
    }

    /**
     *
     * @return 获取表名
     */
    public String getTableName(){
        return this.tableName;
    }

    /**
     *
     * @return 获取表所有列簇List
     */
    public List<String> getColumnFamily(){
        return this.columnFamily;
    }
    protected abstract void setColumnFamily();

    /**
     *
     * @return 获取默认的列簇名（即列簇List第一个元素）
     */
    public String getDefaultColumnName(){
        return this.columnFamily.get(0);
    }
}

