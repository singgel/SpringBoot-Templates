package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseTable;

import java.util.Collections;

public class CFResultTable extends HBaseTable {

    public CFResultTable(CFResultTypeEnum cfResultType){
        super(cfResultType.getName());
    }

    public CFResultTable(String tableName){
        super(tableName);
    }

    @Override
    protected void setColumnFamily() {
        this.columnFamily = Collections.singletonList("c");
    }
}
