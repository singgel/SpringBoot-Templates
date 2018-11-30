package com.hks.hbase.pojo;

import com.hks.hbase.model.HBaseTable;

import java.util.Collections;

public class UserDeviceTable extends HBaseTable {

    public UserDeviceTable() {
        super("user_device_id");
    }

    @Override
    protected void setColumnFamily() {
        this.columnFamily = Collections.singletonList("c");
    }
}
