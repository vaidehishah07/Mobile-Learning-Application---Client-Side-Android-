package com.paril.mlaclientapp.model;



public class MLAFriendRequest {

    Boolean check;

    public Integer userId;
    public String userfirstName;
    public String userlastName;
    public String email;
    public String publicKey;
    public Integer groupNo;
    public String groupKey;


    // for the add frinds to grp API
    public Integer fromuserId;      // id of the login user
    public String groupName;




    public Integer getFromuserId() {
        return fromuserId;
    }

    public void setFromuserId(Integer fromuserId) {
        this.fromuserId = fromuserId;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
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

    public void setGroupKey(String groupkey) {
        this.groupKey = groupkey;
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

    public String getUserfirstName() {
        return userfirstName;
    }

    public void setUserfirstName(String userfirstName) {
        this.userfirstName = userfirstName;
    }

    public String getUserlastName() {
        return userlastName;
    }

    public void setUserlastName(String userlastName) {
        this.userlastName = userlastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publickey) {
        this.publicKey = publickey;


    }

    public MLAFriendRequest(Boolean check, Integer userId, String email, String userfirstName, String userlastName, String publicKey, Integer groupNo, String groupKey) {

        this.userId = userId;
        this.email = email;
        this.userfirstName = userfirstName;
        this.userlastName = userlastName;
        this.publicKey = publicKey;
        this.check = check;
        this.groupNo = groupNo;
        this.groupKey = groupKey;

    }
    public MLAFriendRequest(Integer userId, String email, String userfirstName, String userlastName, String publicKey, Integer groupNo, String groupKey) {

        this.userId = userId;
        this.email = email;
        this.userfirstName = userfirstName;
        this.userlastName = userlastName;
        this.publicKey = publicKey;
        //this.check = check;
        this.groupNo = groupNo;
        this.groupKey = groupKey;

    }

    public MLAFriendRequest() {

    }




}
