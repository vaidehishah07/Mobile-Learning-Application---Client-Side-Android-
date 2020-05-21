package com.paril.mlaclientapp.model;



public class FriendCheckboxModel {

    Integer id;
    String fullName;
    String emailid;
    String publickey;
    String status;
    Boolean check;

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public FriendCheckboxModel(Integer id, String fullName, String emailid, String publickey, Boolean check, String status) {

        this.id = id;
        this.fullName = fullName;
        this.emailid = emailid;
        this.publickey = publickey;
        this.check = check;
        this.status = status;
    }
}
