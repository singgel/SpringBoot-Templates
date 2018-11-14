package io.ymq.mybatis.web;

import com.alibaba.fastjson.JSONObject;
import io.ymq.mybatis.dao.YmqOneBaseDao;
import io.ymq.mybatis.dao.YmqTwoBaseDao;
import io.ymq.mybatis.po.TestOnePo;
import io.ymq.mybatis.po.TestTwoPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 描述:测试 Controller
 *
 * @author yanpenglei
 * @create 2017-10-20 10:32
 **/
@RestController
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private YmqOneBaseDao ymqOneBaseDao;

    @Autowired
    private YmqTwoBaseDao ymqTwoBaseDao;

    @RequestMapping("/")
    public String index() throws Exception {

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

        String onePoList = JSONObject.toJSONString(testOnePoList);
        String twoPoList = JSONObject.toJSONString(testTwoPoList);

        return "数据源 ymqOneBaseDao ：查询结果:" + onePoList + "<br/> 数据源 ymqTwoBaseDao ：查询结果:" + twoPoList;
    }
}
