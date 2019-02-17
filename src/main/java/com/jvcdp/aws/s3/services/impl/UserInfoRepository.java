package com.jvcdp.aws.s3.services.impl;

import com.jvcdp.aws.s3.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query("SELECT t FROM UserInfo t WHERE t.email = ?1")
    UserInfo findByEmail(String emailAddress);
}
