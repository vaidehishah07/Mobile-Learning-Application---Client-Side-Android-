package com.paril.mlaclientapp.model;



public class GDModel
{

    public int groupKeyId;
    public long grpUserId;
    public boolean status;
    public int version;

    public int groupId;
    public String groupName;
    public String groupname;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String groupKey;

    public Integer groupType;
    public Boolean check = false;


    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public boolean isStatus() {
        return status;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupKeyId() {
        return groupKeyId;
    }
    public void setGroupKeyId(int groupKeyId) {
        this.groupKeyId = groupKeyId;
    }

    public long getGrpUserId() {
        return grpUserId;
    }
    public void setGrpUserId(long grpUserId) {
        this.grpUserId = grpUserId;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getGroupKey() {
        return groupKey;
    }
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public GDModel(int groupId, String groupname, String groupKey) {
        this.groupId = groupId;
        this.groupname = groupname;
        this.groupKey = groupKey;
    }
}
