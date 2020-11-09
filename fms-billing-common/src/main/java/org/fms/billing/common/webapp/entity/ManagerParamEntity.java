/**
 * Auth:riozenc
 * Date:2019年3月18日 上午9:49:40
 * Title:com.riozenc.cim.webapp.archives.domain.ManagerParamEntity.java
 **/
package org.fms.billing.common.webapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.riozenc.titanTool.common.json.annotation.IgnorRead;
import com.riozenc.titanTool.mybatis.pagination.Page;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerParamEntity extends Page {

    @JsonProperty(access = Access.WRITE_ONLY)
    @IgnorRead
    private String managerId;
    @JsonProperty(access = Access.WRITE_ONLY)
    @IgnorRead
    private String roleIds;
    @JsonProperty(access = Access.WRITE_ONLY)
    @IgnorRead
    private String deptIds;

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

}