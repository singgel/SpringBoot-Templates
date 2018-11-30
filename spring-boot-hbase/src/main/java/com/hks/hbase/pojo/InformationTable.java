package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseTable;

import java.util.Collections;

public class InformationTable extends HBaseTable {

    public InformationTable() {
        super("information_id");
    }

    @Override
    protected void setColumnFamily() {
        this.columnFamily = Collections.singletonList("c");
    }
}
