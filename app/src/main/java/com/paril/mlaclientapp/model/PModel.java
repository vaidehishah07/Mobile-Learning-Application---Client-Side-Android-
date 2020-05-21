package com.paril.mlaclientapp.model;


public class PModel
{

    String originalfirstname;
    String originallastname;
    int originalownerId;
    String groupkey;

    String publickey;
    int groupId;

    String firstname;
    String lastname;
    int ownerId;
    int postId;
    String postText;
    Integer postType;
    String sessionKey;
    Integer groupNo;
    int originalPostId;
    String digitalSignature;
    String timeStamp;


    public int getOriginalownerId() {
        return originalownerId;
    }

    public void setOriginalownerId(int originalownerId) {
        this.originalownerId = originalownerId;
    }


    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Integer groupNo) {
        this.groupNo = groupNo;
    }

    public int getOriginalPostId() {
        return originalPostId;
    }

    public void setOriginalPostId(int originalPostId) {
        this.originalPostId = originalPostId;
    }

    public String getDigitalSignature() {
        return digitalSignature;
    }

    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getGroupkey() {
        return groupkey;
    }

    public void setGroupkey(String groupkey) {
        this.groupkey = groupkey;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getOriginalfirstname() {
        return originalfirstname;
    }

    public void setOriginalfirstname(String originalfirstname) {
        this.originalfirstname = originalfirstname;
    }

    public String getOriginallastname() {
        return originallastname;
    }

    public void setOriginallastname(String originallastname) {
        this.originallastname = originallastname;
    }








    public PModel(int postId, String postText) {
        this.postId = postId;
        this.postText = postText;
    }

    public PModel(int postId, String postText, String firstname, String lastname, String originalfirstname, String originallastname, Integer originalPostId, Integer postType, Integer groupId) {

        this.postId = postId;
        this.postText = postText;
        this.postType = postType;
        this.firstname = firstname;
        this.lastname = lastname;
        this.originalfirstname = originalfirstname;
        this.originallastname = originallastname;
        this.originalPostId = originalPostId;
        this.groupId = groupId;
    }

    public PModel(int postId, String postText, String firstname, String lastname, String originalfirstname, String originallastname, Integer originalPostId, Integer postType, Integer groupId, String groupkey, String sessionKey ) {

        this.postId = postId;
        this.postText = postText;
        this.postType = postType;
        this.firstname = firstname;
        this.lastname = lastname;
        this.originalfirstname = originalfirstname;
        this.originallastname = originallastname;
        this.originalPostId = originalPostId;
        this.groupId = groupId;
        this.groupkey = groupkey;
        this.sessionKey = sessionKey;


    }




    public PModel()
    {

    }
}