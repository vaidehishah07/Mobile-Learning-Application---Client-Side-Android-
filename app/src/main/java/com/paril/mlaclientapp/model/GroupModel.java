package com.paril.mlaclientapp.model;


public class GroupModel
{

    Boolean check;

   // for the display friends API
    public Integer userId;
    public String firstName;
    public String lastName;
    public String emailId;
    public String publickey;

    // for the add friends to grp API
    public Integer ownerId;
    public String groupName;
    public String groupKey;
    public String groupType;
    public Integer groupId;


    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }


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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


    public String getPublicKey() {
        return publickey;
    }

    public void setPublicKey(String publickey) {
        this.publickey = publickey;
    }




    public GroupModel(Boolean check, Integer userId, String emailId, String firstName, String lastName, String publickey) {

        this.userId = userId;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publickey = publickey;
        this.check = check;

    }
    public GroupModel(Boolean check, Integer userId, String emailId, String firstName, String lastName, String publickey, Integer groupId, String groupName, String groupKey) {

        this.userId = userId;
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.publickey = publickey;
        this.groupKey = groupKey;
        this.groupName = groupName;
        this.groupId = groupId;
        this.check = check;


    }




    public GroupModel(String groupName, Integer groupId) {

        this. groupName = groupName;
        this. groupId = groupId;

    }


    public GroupModel()
    {


    }
    @Override
    public String toString()
    {
        return groupName;
    }


    public GroupModel(Integer userId, String firstName, String lastName, String emailId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }
}
