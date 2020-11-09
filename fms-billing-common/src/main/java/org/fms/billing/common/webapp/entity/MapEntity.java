package org.fms.billing.common.webapp.entity;

import com.riozenc.titanTool.mybatis.MybatisEntity;

public class MapEntity implements MybatisEntity {
    private Long key;
    private Object value;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
