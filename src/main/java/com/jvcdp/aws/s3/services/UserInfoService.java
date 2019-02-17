package com.jvcdp.aws.s3.services;

import com.jvcdp.aws.s3.model.UserInfo;

public interface UserInfoService {
    UserInfo findByEmail(String emailAddress) throws Exception;
    boolean addUser(String emailAddress, String password) throws Exception;

    boolean authenticate(String emailAddress, String password);
}
