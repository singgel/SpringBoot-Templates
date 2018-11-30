package com.hks.hbase.pojo;


import com.hks.hbase.model.HBaseCell;

import java.util.HashMap;
import java.util.Map;

public class InformationCell extends HBaseCell {

    public int getMergeId() {
        return mergeId;
    }

    public void setMergeId(Integer mergeId) {
        this.mergeId = mergeId;
    }
    /**
     * cell ID值
     */
    private Integer mergeId;
    private Integer objId;
    private Integer objType;
    /**
     *
     * @param objId
     * @param objType
     * @param mergeId
     */
    public InformationCell(Integer objId,Integer objType,Integer mergeId) {
        super("c");
        setRowKey(objId+":"+objType);
        setMergeId(mergeId);
        setObjId(objId);
        setObjType(objType);
    }

    @Override
    public Map<String, byte[]> getCellMap() {
        Map<String, byte[]> cellMap = new HashMap<>();
        cellMap.put(getIdCellName(), getBytes(Integer.toString(getMergeId())));
        String rowkey = getRowKey();
        cellMap.put("idandtype",getBytes(rowkey));
        cellMap.put("objid",getBytes(getObjId()));
        cellMap.put("objtype",getBytes(getObjType()));
        return cellMap;
    }

    public String getIdCellName(){
        return "id";
    }

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }
}