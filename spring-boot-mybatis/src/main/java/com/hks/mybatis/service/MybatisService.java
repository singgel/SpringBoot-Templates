package com.hks.mybatis.service;

import com.hks.mybatis.dao.YmqOneBaseDao;
import com.hks.mybatis.po.TestOnePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MybatisService{

    @Autowired
    YmqOneBaseDao ymqOneBaseDao;

    @Transactional(value = "txManager1", rollbackFor = Exception.class)
    public boolean Insert(TestOnePo testOnePo) {
        ymqOneBaseDao.insert(testOnePo);

        insert();
        return true;
    }

    public boolean insert() {

        TestOnePo testOnePoo = new TestOnePo();
        testOnePoo.setId(1111111L);
        testOnePoo.setName("aaaaaaaaaa");
        testOnePoo.setRemark("bbbbbbbbbb");

        ymqOneBaseDao.insert(testOnePoo);
        return true;
    }
}
