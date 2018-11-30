package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseCell;

import java.util.HashMap;
import java.util.Map;

public class UserDeviceCell extends HBaseCell {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * cell ID值
     */
    private Integer id;
    public UserDeviceCell(String rowKey,Integer id) {
        super("c");
        setRowKey(rowKey);
        setId(id);
    }

    @Override
    public Map<String, byte[]> getCellMap() {
        Map<String, byte[]> cellMap = new HashMap<>();
        cellMap.put(getIdCellName(), getBytes(getId()));
        cellMap.put("uidordevid",getBytes(getRowKey()));
        return cellMap;
    }

    public String getIdCellName(){
        return "id";
    }
}
