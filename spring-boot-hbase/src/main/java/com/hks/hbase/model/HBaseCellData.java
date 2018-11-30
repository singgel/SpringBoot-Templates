package com.hks.hbase.model;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * HBase中对应的cell数据model
 * 存储的value必须为string类型
 */
public class HBaseCellData {
    private String columnFamily;
    private String cellKey;
    private String cellValue;
    private long timestamp;

    public HBaseCellData(Cell cell){
        if(cell != null){
            String column = Bytes.toString(CellUtil.cloneFamily(cell));
            String cellKey = Bytes.toString(CellUtil.cloneQualifier(cell));
            String cellValue = Bytes.toString(CellUtil.cloneValue(cell));

            setColumnFamily(column);
            setCellKey(cellKey);
            setCellValue(cellValue);
            setTimestamp(cell.getTimestamp());
        }

    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public String getCellKey() {
        return cellKey;
    }

    public void setCellKey(String cellKey) {
        this.cellKey = cellKey;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }
}