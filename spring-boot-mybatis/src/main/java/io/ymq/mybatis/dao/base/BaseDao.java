package io.ymq.mybatis.dao.base;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述: SqlSessionDaoSupport
 * author: yanpenglei
 * Date: 2017/9/8 19:50
 */
public class BaseDao extends SqlSessionDaoSupport implements IBaseDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected static final String POSTFIX_COUNT = ".count";
    protected static final String POSTFIX_SELECTONE = ".selectOne";
    protected static final String POSTFIX_INSERT = ".insert";
    protected static final String POSTFIX_INSERTLIST = ".insertList";
    protected static final String POSTFIX_UPDATE = ".update";
    protected static final String POSTFIX_UPDATE_BY_ENTITY = ".updateByEntity";
    protected static final String POSTFIX_DELETE = ".delete";
    protected static final String POSTFIX_SELECTLIST = ".selectList";
    protected static final String POSTFIX_SELECTLISTORDERBY = ".selectListOrderBy";

    @Override
    public <T> Integer count(Object entity) {

        String className = entity.getClass().getName();
        return getSqlSession().selectOne(className + POSTFIX_COUNT, entity);

    }

    @Override
    public <T> Integer count(String statementPostfix, Object object) {

        return getSqlSession().selectOne(statementPostfix, object);

    }

    @Override
    public <T> T selectOne(String statement) {

        return getSqlSession().selectOne(statement);

    }

    @Override
    public <T> T selectOne(Object entity) {

        String className = entity.getClass().getName();
        return getSqlSession().selectOne(className + POSTFIX_SELECTONE, entity);

    }

    @Override
    public <T> T selectOne(String statementPostfix, Object object) {

        return getSqlSession().selectOne(statementPostfix, object);

    }

    @Override
    public <T> int insert(String statement, Object obj) {

        return getSqlSession().insert(statement, obj);

    }

    @Override
    public <T> int insert(T entity) {

        String className = entity.getClass().getName();
        return getSqlSession().insert(className + POSTFIX_INSERT, entity);

    }

    @Override
    public <T> int insert(List<T> list) {

        if (list.size() == 0) {
            return list.size();
        }
        String className = list.get(0).getClass().getName();
        return getSqlSession().insert(className + POSTFIX_INSERTLIST, list);

    }

    @Override
    public <T> int update(String statement) {

        return getSqlSession().update(statement);

    }

    @Override
    public <T> int update(Object entity) {

        String className = entity.getClass().getName();
        return getSqlSession().update(className + POSTFIX_UPDATE, entity);

    }

    /**
     * 更新对象 (注意:传入参数必须保证参数的可靠性，不然可能会导致错误的更新数据,请谨慎使用)
     *
     * @param setParameter   update语句中用于set的参数
     * @param whereParameter update语句中用于where条件的参数
     * @return @
     */
    @Override
    @Deprecated
    public <T> int update(Object setParameter, Object whereParameter) {

        if (null == setParameter && null == whereParameter) {
            logger.warn("updateByEntity param setParameter  or whereParameter is null ");
            return 0;
        }
        String className = setParameter.getClass().getName();
        Map<String, Object> parameter = new HashMap<String, Object>(2);
        parameter.put("s", setParameter);
        parameter.put("w", whereParameter);
        return update(className + POSTFIX_UPDATE_BY_ENTITY, parameter);
    }

    @Override
    public <T> int update(String statementPostfix, Object entity) {

        return getSqlSession().update(statementPostfix, entity);

    }

    /**
     * 删除(注意:删除时一定要保证传入参数的正确性)
     *
     * @param entity
     * @return @
     */
    @Override
    public <T> int delete(Object entity) {

        if (null == entity) {
            logger.warn("can not delete data , entity is null !!!");
            return 0;
        }
        String className = entity.getClass().getName();
        return getSqlSession().delete(className + POSTFIX_DELETE, entity);

    }

    @Override
    public <T> int delete(String statementPostfix, Object entity) {

        return getSqlSession().delete(statementPostfix, entity);

    }

    @Override
    public <T> List<T> selectList(String statement) {

        return getSqlSession().selectList(statement);

    }

    @Override
    public <T> List<T> selectList(Object entity) {

        String statementPostfix = entity.getClass().getName() + POSTFIX_SELECTLIST;
        return selectList(statementPostfix, entity, null);
    }

    @Override
    public <T> List<T> selectList(T entity, String orderBy) {

        String statementPostfix = entity.getClass().getName() + POSTFIX_SELECTLIST;
        return selectList(statementPostfix, entity, orderBy);
    }

    @Override
    public <T> List<T> selectList(String statementPostfix, Object entity) {

        return selectList(statementPostfix, entity, null);
    }

    @Override
    public <T> List<T> selectList(String statementPostfix, Object entity, String orderBy) {

        if (!StringUtils.isEmpty(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        return getSqlSession().selectList(statementPostfix, entity);

    }

    @Override
    public <T> List<T> selectList(Object entity, int pageNum, int pageSize) {

        String statementPostfix = entity.getClass().getName() + POSTFIX_SELECTLIST;

        return selectList(statementPostfix, entity, pageNum, pageSize, null);
    }

    @Override
    public <T> List<T> selectList(Object entity, int pageNum, int pageSize, String orderBy) {

        String statementPostfix = entity.getClass().getName() + POSTFIX_SELECTLIST;

        return selectList(statementPostfix, entity, pageNum, pageSize, orderBy);
    }

    @Override
    public <T> List<T> selectList(String statementPostfix, Object entity, int pageNum, int pageSize) {

        return selectList(statementPostfix, entity, pageNum, pageSize, null);
    }

    @Override
    public <T> List<T> selectList(String statementPostfix, Object entity, int pageNum, int pageSize, String orderBy) {

        PageHelper.startPage(pageNum, pageSize, false);

        if (!StringUtils.isEmpty(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<T> list = getSqlSession().selectList(statementPostfix, entity);

        return list;

    }

    @Override
    public <T> QueryResult<T> selectListAndCount(String statementPostfix, Object entity, int pageNum, int pageSize, String orderBy) {

        Page<?> page = PageHelper.startPage(pageNum, pageSize);

        if (!StringUtils.isEmpty(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<T> list = getSqlSession().selectList(statementPostfix, entity);
        QueryResult<T> result = new QueryResult<>(list, page.getTotal());

        return result;

    }

    @Override
    public <T> QueryResult<T> selectListAndCount(String statementPostfix, Object entity, int pageNum, int pageSize, String orderBy, String statementCount) {

        List<T> list = getSqlSession().selectList(statementPostfix, entity);
        long count = this.selectOne(statementCount, entity);
        QueryResult<T> result = new QueryResult<>(list, count);

        return result;

    }

    @SuppressWarnings("unused")
    private <T> Map<String, Object> bean2Map(T entity) throws Exception {

        BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Object value = descriptor.getReadMethod().invoke(entity);
            map.put(propertyName, value);
        }
        return map;
    }

}
