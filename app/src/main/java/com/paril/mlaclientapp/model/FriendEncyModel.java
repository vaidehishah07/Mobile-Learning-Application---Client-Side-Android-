package com.paril.mlaclientapp.model;

/**
 * Created by Vaidehi Shah on 04-25-2020.
 */


public class FriendEncyModel {


    public Integer userId; // current user Id
    public String groupKey;
    public Integer friendId; // list of

    public Integer groupId;

    public Integer getFromuserid() {
        return userId;
    }

    public void setFromuserid(Integer fromuserId) {
        this.userId = fromuserId;
    }

    public String getGroupkey() {
        return groupKey;
    }

    public void setGroupkey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Integer getTouserid() {
        return friendId;
    }

    public void setTouserid(Integer touserId) {
        this.friendId = touserId;
    }
}
