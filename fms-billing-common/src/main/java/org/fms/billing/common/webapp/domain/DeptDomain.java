package org.fms.billing.common.webapp.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeptDomain {
    private Long id;
    private Long parentId;
    private String deptName;  // 营业区域 DEPT_NAME varchar(64)
    private String title;
    private Integer depeType;
    private List<DeptDomain> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDepeType() {
        return depeType;
    }

    public void setDepeType(Integer depeType) {
        this.depeType = depeType;
    }

    public List<DeptDomain> getChildren() {
        return children;
    }

    public void setChildren(List<DeptDomain> children) {
        this.children = children;
    }
}
