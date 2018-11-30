package com.hks.hbase.model;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.Map;

import static com.hks.hbase.util.CheckUtil.checkIsEmpty;

public abstract class HBaseCell {
    private String columnName;

    protected String rowKey;

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public HBaseCell(String columnName) {
        checkIsEmpty(columnName, "列簇名不能为空");
        this.columnName = columnName;
    }

    /**
     * @return 获取列簇名
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * @return 获取列簇中key-value字节数组Map
     */
    public abstract Map<String, byte[]> getCellMap();


    /**
     * @return 获取列簇字节数组
     */
    public byte[] getColumnNameBytes() {
        return Bytes.toBytes(getColumnName());
    }

    /**
     * @param val
     * @return 获取byte数组
     */
    protected byte[] getBytes(String val) {
        if (val == null) {
            val = "";
        }
        return Bytes.toBytes(val);
    }

    /**
     * @param val
     * @return 获取byte数组
     */
    protected byte[] getBytes(Long val) {
        if (val == null) {
            val = 0L;
        }
        return Bytes.toBytes(val);
    }

    /**
     * @param val
     * @return 获取byte数组
     */
    protected byte[] getBytes(Integer val) {
        if (val == null) {
            val = 0;
        }
        return Bytes.toBytes(val);
    }

    /**
     * @param val
     * @return 获取byte数组
     */
    protected byte[] getBytes(Boolean val) {
        if (val == null) {
            return null;
        }
        return Bytes.toBytes(val);
    }
}