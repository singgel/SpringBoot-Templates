package com.hks.hbase.controller;

import com.hks.hbase.model.HBaseTable;
import com.hks.hbase.pojo.UserDeviceTable;
import com.hks.hbase.service.IHBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: singgel
 * @Date: 2018/11/30
 */
@RestController
@RequestMapping("/hbase")
public class HbaseDemoController {

    @Autowired
    IHBaseService ihBaseService;

    @RequestMapping(value = "create",method = RequestMethod.GET)
    public String create(){
        UserDeviceTable table = new UserDeviceTable();
        ihBaseService.createTable(table);
        return "success";
    }
}
