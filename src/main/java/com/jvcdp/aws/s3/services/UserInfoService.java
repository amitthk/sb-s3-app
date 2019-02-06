package com.jvcdp.aws.s3.services;

import com.jvcdp.aws.s3.model.UserInfo;

public interface UserInfoService {
    public UserInfo findByEmail(String emailAddress);
    public UserInfo addUser(UserInfo userDetails);

    boolean authenticate(String emailAddress, String password);
}
