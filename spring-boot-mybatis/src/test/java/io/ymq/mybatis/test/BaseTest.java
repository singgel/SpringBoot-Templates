package io.ymq.mybatis.test;


import com.alibaba.fastjson.JSONObject;
import io.ymq.mybatis.dao.YmqOneBaseDao;
import io.ymq.mybatis.dao.YmqTwoBaseDao;
import io.ymq.mybatis.po.TestOnePo;
import io.ymq.mybatis.po.TestTwoPo;
import io.ymq.mybatis.run.Startup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 描述: 单元测试
 * author: yanpenglei
 * Date: 2017/10/19 19:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    private YmqOneBaseDao ymqOneBaseDao;

    @Autowired
    private YmqTwoBaseDao ymqTwoBaseDao;

    @Test
    public void test() throws Exception {

        List<TestOnePo> testOnePoList = null;

        testOnePoList = ymqOneBaseDao.selectList(new TestOnePo());

        for (TestOnePo item : testOnePoList) {
            LOG.info("数据源 ymqOneBaseDao ：查询结果:{}", JSONObject.toJSONString(item));
        }

        List<TestTwoPo> testTwoPoList = null;

        testTwoPoList = ymqTwoBaseDao.selectList(new TestTwoPo());

        for (TestTwoPo item : testTwoPoList) {
            LOG.info("数据源 ymqTwoBaseDao：查询结果:{}", JSONObject.toJSONString(item));
        }

    }

}