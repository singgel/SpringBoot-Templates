
package io.ymq.mybatis.po;

import java.io.Serializable;


public class TestTwoPo implements Serializable {
    private static final long serialVersionUID = 1L;
    //alias
    public static final String TABLE_ALIAS = "TestTwo";


    //columns START
    /**
     * id       db_column: id
     */
    private Long id;
    /**
     * 名称       db_column: name
     */
    private String name;
    /**
     * 备注       db_column: remark
     */
    private String remark;
    //columns END


    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }


}

