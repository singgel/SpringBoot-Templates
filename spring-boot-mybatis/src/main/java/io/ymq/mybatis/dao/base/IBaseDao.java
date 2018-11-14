package io.ymq.mybatis.dao.base;



import java.util.List;

/**
 * 描述: IBaseDao
 * author: yanpenglei
 * Date: 2017/9/8 19:50
 */
public interface IBaseDao {

    /**
     * 查询统计 根据对象条件统计
     *
     * @param object
     * @return
     */
    public <T> Integer count(Object object);

    /**
     * 查询统计
     *
     * @param statementPostfix 自定义方法
     * @param object           PO对象
     * @return
     */
    public <T> Integer count(String statementPostfix, Object object);

    /**
     * 自定义查询
     *
     * @param statement
     * @return
     */
    public <T> T selectOne(String statement);

    /**
     * 根据 T 查询数据
     *
     * @param object
     * @return
     */
    public <T> T selectOne(Object object);

    /**
     * 自定义查询数据
     *
     * @param statementPostfix 自定义方法名称
     * @param object           数据对象
     * @return
     */
    public <T> T selectOne(String statementPostfix, Object object);

    /**
     * 插入对象
     *
     * @param statement
     * @param obj
     * @return @
     */
    public <T> int insert(String statement, Object obj);

    /**
     * 插入一条记录
     *
     * @param entity
     * @return @
     */
    public <T> int insert(T entity);

    /**
     * 批量插入数据
     *
     * @param list
     * @return @
     */
    public <T> int insert(List<T> list);

    /**
     * 自定义更新
     *
     * @param statement
     * @return @
     */
    public <T> int update(String statement);

    /**
     * 更新对象(此方法只根据主键ID进行更新)
     *
     * @param entity
     * @return @
     */
    public <T> int update(Object entity);

    /**
     * 更新对象 (注意:传入参数必须保证参数的可靠性，不然可能会导致错误的更新数据,请谨慎使用)
     *
     * @param setParameter   update语句中用于set的参数
     * @param whereParameter update语句中用于where条件的参数
     * @return @
     */
    public <T> int update(Object setParameter, Object whereParameter);

    /**
     * 自定义更新方法
     *
     * @param statementPostfix 方法路径名称
     * @param entity
     * @return @
     */
    public <T> int update(String statementPostfix, Object entity);

    /**
     * 删除
     *
     * @param entity
     * @return @
     */
    public <T> int delete(Object entity);

    /**
     * 自定义删除方法
     *
     * @param statementPostfix
     * @param entity
     * @return @
     */
    public <T> int delete(String statementPostfix, Object entity);

    /**
     * 自定义查询
     *
     * @param statement
     * @return
     */
    public <T> List<T> selectList(String statement);

    /**
     * 查询一个列表， 不分页,不排序
     *
     * @param entity 实体对象
     * @return
     */
    public <T> List<T> selectList(Object entity);

    /**
     * 查询一个列表， 不分页,不排序
     *
     * @param statementPostfix 自定义sql名称
     */
    public <T> List<T> selectList(String statementPostfix, Object entity);

    /**
     * 查询一个列表， 不分页
     *
     * @param entity  实体对象
     * @param orderBy 排序sql 例：'id desc'
     * @return
     */
    public <T> List<T> selectList(T entity, String orderBy);

    /**
     * 查询一个列表， 不分页
     *
     * @param statementPostfix 自定义sql名称
     * @param orderBy          排序sql 例：'id desc'
     */
    public <T> List<T> selectList(String statementPostfix, Object entity, String orderBy);

    /**
     * 分页查询 不排序
     *
     * @param entity
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @return
     */
    public <T> List<T> selectList(Object entity, int pageNum, int pageSize);

    /**
     * 分页查询 不排序
     *
     * @param statementPostfix
     * @param entity
     * @param pageNum          页码
     * @param pageSize         每页记录数
     * @return
     */
    public <T> List<T> selectList(String statementPostfix, Object entity, int pageNum, int pageSize);

    /**
     * 分页查询
     *
     * @param entity
     * @param pageNum  页码
     * @param pageSize 每页记录数
     * @param orderBy  排序sql 例：'id desc'
     * @return
     */
    public <T> List<T> selectList(Object entity, int pageNum, int pageSize, String orderBy);

    /**
     * 分页查询
     *
     * @param statementPostfix
     * @param entity
     * @param pageNum          页码
     * @param pageSize         每页记录数
     * @param orderBy          排序sql 例：'id desc'
     * @return
     */
    public <T> List<T> selectList(String statementPostfix, Object entity, int pageNum, int pageSize, String orderBy);

    /**
     * 此方法返回数据结果集和count
     *
     * @param statementPostfix 自定义方法名(mybatis namespace)
     * @param entity           参数对象
     * @param pageNum          页码
     * @param pageSize         每页记录数
     * @param orderBy          排序字段(例：'id desc')
     * @return
     */
    public <T> QueryResult<T> selectListAndCount(String statementPostfix, Object entity, int pageNum, int pageSize, String orderBy);

    /**
     * 此方法返回数据结果集和count
     *
     * @param statementPostfix 自定义方法名(mybatis namespace)
     * @param entity           参数对象
     * @param pageNum          页码
     * @param pageSize         每页记录数
     * @param orderBy          排序字段(例：'id desc')
     * @param statementCount   自定义count查询语句
     * @return
     */
    public <T> QueryResult<T> selectListAndCount(String statementPostfix, Object entity, int pageNum, int pageSize, String orderBy, String statementCount);
}
