package com.jvcdp.aws.s3.model;

import org.joda.time.DateTime;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private Long id;
    private String name;
    private String userName;
    private String passwordHash;
    private String email;
    private String salt;
    private DateTime lastLogin;

    public UserInfo() {
        super();
    }

    public UserInfo(Long id, String name, String userName, String passwordHash, String email, String salt) {
        super();
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.email = email;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public DateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(DateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
