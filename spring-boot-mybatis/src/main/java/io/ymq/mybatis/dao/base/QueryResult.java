package io.ymq.mybatis.dao.base;

import java.util.List;

/**
 * 描述: 分页返回实体
 * author: yanpenglei
 * Date: 2017/9/8 15:05
 */
public class QueryResult<T> implements java.io.Serializable {
    private static final long serialVersionUID = 461900815434592315L;

    private List<T> list;
    private long total;

    public QueryResult() {

    }

    public QueryResult(List<T> list, long total) {
        super();
        this.list = list;
        this.total = total;
    }

    public List<T> getlist() {
        return list;
    }

    public void setlist(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}


