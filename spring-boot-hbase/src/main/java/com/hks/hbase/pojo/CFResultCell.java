package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseCell;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CFResultCell extends HBaseCell {

    /**
     * 用fastjson序列化的ArrayList<RecommendModel>对象
     */
    private String resultJson;
    private static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public CFResultCell(String rowKey,String resultJson) {
        super("c");
        this.resultJson = resultJson;
        setRowKey(rowKey);
    }

    @Override
    public Map<String, byte[]> getCellMap() {
        Map<String,byte[]> map = new HashMap<>();
        map.put("r", Bytes.toBytes(resultJson));
        map.put("rowkey",Bytes.toBytes(getRowKey()));
        map.put("time",Bytes.toBytes(dateformat.format(new Date())));
        return map;
    }

    public String getDefaultCellName(){
        return "r";
    }
}
