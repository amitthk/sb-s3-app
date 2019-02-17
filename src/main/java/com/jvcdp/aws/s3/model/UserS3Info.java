package com.jvcdp.aws.s3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserS3Info {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String awsId;

    private String awsKey;

    private String region;

    private String currentS3Bucket;

    public UserS3Info() {
    }

    public UserS3Info(Long id, Long userId, String awsId, String awsKey, String region, String currentS3Bucket) {
        this.id = id;
        this.userId = userId;
        this.awsId = awsId;
        this.awsKey = awsKey;
        this.region = region;
        this.currentS3Bucket = currentS3Bucket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAwsId() {
        return awsId;
    }

    public void setAwsId(String awsId) {
        this.awsId = awsId;
    }

    public String getAwsKey() {
        return awsKey;
    }

    public void setAwsKey(String awsKey) {
        this.awsKey = awsKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrentS3Bucket() {
        return currentS3Bucket;
    }

    public void setCurrentS3Bucket(String currentS3Bucket) {
        this.currentS3Bucket = currentS3Bucket;
    }
}
