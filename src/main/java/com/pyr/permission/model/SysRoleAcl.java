package com.pyr.permission.model;

import java.util.Date;

public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String remark;

    private String creator;

    private Date createTime;

    private String creatorip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAclId() {
        return aclId;
    }

    public void setAclId(Integer aclId) {
        this.aclId = aclId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorip() {
        return creatorip;
    }

    public void setCreatorip(String creatorip) {
        this.creatorip = creatorip == null ? null : creatorip.trim();
    }
}