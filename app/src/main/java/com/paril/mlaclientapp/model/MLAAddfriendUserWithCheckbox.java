package com.paril.mlaclientapp.model;

/**
 * Created by Vaidehi Shah on 04-12-2020.
 */

public class MLAAddfriendUserWithCheckbox
{

    Integer userId;
    String userName;
    String userType;
    Boolean check;

    public String getUsername() {
        return userName;
    }

    public String getUsertype() {
        return userType;
    }

    public void setUsertype(String email) {
        this.userType = userType;
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

    public MLAAddfriendUserWithCheckbox(String userName, String userType, Boolean check, Integer userId) {

        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.check = check;


    }




    public MLAAddfriendUserWithCheckbox(){

    }





}
