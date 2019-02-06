package com.jvcdp.aws.s3.services;

import com.jvcdp.aws.s3.model.UserInfo;

public interface UserInfoService {
    UserInfo findByEmail(String emailAddress);
    boolean addUser(String emailAddress, String password);

    boolean authenticate(String emailAddress, String password);
}
