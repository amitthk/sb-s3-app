package com.jvcdp.aws.s3.services.impl;

import com.jvcdp.aws.s3.model.UserS3Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserS3InfoRepository extends JpaRepository<UserS3Info, Long> {
}
