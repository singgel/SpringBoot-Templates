package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseTable;

import java.util.Collections;

/**
 * 资讯信息表，rowkey为聚合id，column为objId、objType
 */
public class CFIdInfoTable extends HBaseTable {

    public CFIdInfoTable() {
        super("cf_id_info");
    }

    @Override
    protected void setColumnFamily() {
        this.columnFamily = Collections.singletonList("c");
    }
}
