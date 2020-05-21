package com.paril.mlaclientapp.model;


public class MLALeaveGroup
{
    Boolean check;

    // for the display  list of group API
    public Integer userId;
    public Integer groupId;
    public Integer groupType;
    public String groupname;
    public String groupKey;


    public Integer ownerId;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }


    public MLALeaveGroup(Integer userId, String groupname, Integer groupId, Integer groupType, String groupKey) {

        this.userId = userId;
        this.groupId = groupId;
        this.groupType = groupType;
        this.groupname = groupname;
        this.groupKey = groupKey;
    }
}
