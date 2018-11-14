package io.ymq.solr.utils;


import io.ymq.solr.pagehelper.PageInfo;
import io.ymq.solr.pagehelper.PageInfoFacet;
import io.ymq.solr.pagehelper.RowBounds;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * 描述: CloudSolrClient 工具类
 *
 * @author yanpenglei
 * @create 2017-10-19 10:56
 **/
public interface BaseSolr {

    /**
     * 添加数据
     *
     * @param defaultCollection solr 库
     * @param bean              对象
     * @throws IOException
     * @throws SolrServerException
     */
    public void add(String defaultCollection, Object bean) throws IOException, SolrServerException;

    /**
     * 添加一组数据
     *
     * @param defaultCollection solr 库
     * @param beans             list集合数据添加
     * @throws IOException
     * @throws SolrServerException
     */
    public void adds(String defaultCollection, Collection<?> beans) throws IOException, SolrServerException;

    /**
     * 根据多个id删除数据
     *
     * @param defaultCollection
     * @param ids
     * @throws IOException
     * @throws SolrServerException
     */
    public void deleteByIds(String defaultCollection, List<String> ids) throws IOException, SolrServerException;

    /**
     * 根据ID删除数据
     *
     * @param defaultCollection solr 库
     * @param id                要删除的文档的id
     * @throws IOException
     * @throws SolrServerException
     */
    public void deleteById(String defaultCollection, String id) throws IOException, SolrServerException;

    /**
     * 根据指定索引(字段)模糊删除数据
     *
     * @param defaultCollection solr 库
     * @param field
     * @param fieldValue
     * @throws IOException
     * @throws SolrServerException
     */
    public void deleteByField(String defaultCollection, String field, String fieldValue) throws IOException, SolrServerException;

    /**
     * 查询数据
     *
     * @param defaultCollection solr 库
     * @param clazz             对象Po
     * @param query             查询条件
     * @param <T>               返回查询集合
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> List<T> query(String defaultCollection, Class<T> clazz, SolrQuery query) throws IOException, SolrServerException;

    /**
     * 查询
     *
     * @param defaultCollection solr 库
     * @param query             查询条件
     * @return 返回response对象
     * @throws IOException
     * @throws SolrServerException
     */
    public QueryResponse query(String defaultCollection, SolrQuery query) throws IOException, SolrServerException;

    /**
     * @param defaultCollection solr 库
     * @param clazz             查询的数据对应的对象
     * @param query             查询条件
     * @param rowBounds         分页参数
     * @param <T>
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public <T> PageInfo query(String defaultCollection, Class<T> clazz, SolrQuery query, RowBounds rowBounds) throws IOException, SolrServerException;

    /**
     * 查询数据
     *
     * @param defaultCollection solr 库
     * @param query             查询条件
     * @param rowBounds         分页
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public PageInfo query(String defaultCollection, SolrQuery query, RowBounds rowBounds) throws IOException, SolrServerException;

    /**
     * solrj的facet结果集查询
     *
     * @param defaultCollection solr 库
     * @param query             查询条件
     * @param rowBounds         分页数
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public PageInfoFacet queryFacet(String defaultCollection, SolrQuery query, RowBounds rowBounds) throws IOException, SolrServerException;
}
