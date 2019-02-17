package com.jvcdp.aws.s3.services.impl;

import com.jvcdp.aws.s3.model.UserInfo;
import com.jvcdp.aws.s3.model.UserS3Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserS3InfoRepository extends JpaRepository<UserS3Info, Long> {
    @Query("SELECT t FROM UserS3Info t WHERE t.userId = ?1")
    List<UserS3Info> findByUserId(Long userId);
}
