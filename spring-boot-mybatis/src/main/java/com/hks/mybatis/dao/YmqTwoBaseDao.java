package com.hks.mybatis.dao;

import com.hks.mybatis.dao.base.BaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 描述:数据源 two
 *
 * @author hekuangsheng
 * @create 2017-10-20 11:27
 **/
@Repository
public class YmqTwoBaseDao extends BaseDao {

    @Resource
    public void setSqlSessionFactorYmqTwo(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
