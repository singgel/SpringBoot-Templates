package io.ymq.solr.po;


import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;

/**
 * 描述: 映射的实体类必须有@ID主键
 *
 * @author yanpenglei
 * @create 2017-10-17 18:28
 **/
@SolrDocument(solrCoreName = "test_collection")
public class Ymq implements Serializable {

    @Id
    @Field
    private String id;

    @Field
    private String ymqTitle;

    @Field
    private String ymqUrl;

    @Field
    private String ymqContent;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYmqTitle() {
        return ymqTitle;
    }

    public void setYmqTitle(String ymqTitle) {
        this.ymqTitle = ymqTitle;
    }

    public String getYmqUrl() {
        return ymqUrl;
    }

    public void setYmqUrl(String ymqUrl) {
        this.ymqUrl = ymqUrl;
    }

    public String getYmqContent() {
        return ymqContent;
    }

    public void setYmqContent(String ymqContent) {
        this.ymqContent = ymqContent;
    }
}
