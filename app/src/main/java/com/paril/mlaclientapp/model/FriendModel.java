package com.paril.mlaclientapp.model;


public class FriendModel
{

    public String userfirstName;
    public String userlastName;
    public Integer userId;
    public String email;
    public String publicKey;
    public String groupKey;

    public Integer groupId;
    public Integer position = 0;
    public String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getUserfirstName() {
        return userfirstName;
    }

    public void setUserfirstName(String userfirstName) {
        this.userfirstName = userfirstName;
    }

    public String getFullName(){
        return userfirstName + userlastName;
    }

    public String getUserlastName() {
        return userlastName;
    }

    public void setUserlastName(String userlastName) {
        this.userlastName = userlastName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FriendModel(String userfirstName, String userlastName, Integer userId, String email) {
        this.userfirstName = userfirstName;
        this.userlastName = userlastName;
        this.userId = userId;
        this.email = email;
    }
    public FriendModel() {

    }
}
