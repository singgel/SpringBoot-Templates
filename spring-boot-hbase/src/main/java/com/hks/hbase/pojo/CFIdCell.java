package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseCell;

import java.util.HashMap;
import java.util.Map;

public class CFIdCell extends HBaseCell {

    private String objIdCell;

    public String getObjIdCell() {
        return objIdCell;
    }

    public String getObjTypeCell() {
        return objTypeCell;
    }

    public String getObjId() {
        return objId;
    }
    public void setObjId(String objId) {
        this.objId = objId;
    }
    public String getObjType() {
        return objType;
    }
    public void setObjType(String objType) {
        this.objType = objType;
    }

    private String objTypeCell;
    private String objId;
    private String objType;

    public CFIdCell(String rowKey,String objId,String objType) {
        super("c");
        this.setRowKey(rowKey);
        this.objIdCell = "id";
        this.objTypeCell = "type";
        this.setObjId(objId);
        this.setObjType(objType);
    }

    /**
     * @return 获取列簇中key-value字节数组Map
     */
    @Override
    public Map<String, byte[]> getCellMap() {
        Map<String, byte[]> cellMap = new HashMap<>();
        cellMap.put(getObjIdCell(), getBytes(getObjId()));
        cellMap.put(getObjTypeCell(), getBytes(getObjType()));
        cellMap.put("mergeid",getBytes(getRowKey()));
        return cellMap;
    }
}