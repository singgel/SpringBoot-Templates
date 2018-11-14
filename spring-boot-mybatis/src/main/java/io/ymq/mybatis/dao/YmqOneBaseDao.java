package io.ymq.mybatis.dao;

import io.ymq.mybatis.dao.base.BaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 描述:数据源 one
 *
 * @author yanpenglei
 * @create 2017-10-20 11:27
 **/
@Repository
public class YmqOneBaseDao extends BaseDao {

    @Resource
    public void setSqlSessionFactorYmqOne(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}
