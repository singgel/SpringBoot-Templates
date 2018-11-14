package io.ymq.solr.pagehelper;

/**
 * 分页查询接口
 *
 * @author liuzh_3nofxnp
 * @since 2015-12-18 18:51
 */
public interface ISelect {

    /**
     * 在接口中调用自己的查询方法，不要在该方法内写过多代码，只要一行查询方法最好
     */
    void doSelect();

}
