package com.jvcdp.aws.s3.services.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.swagger.annotations.Scope;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.PostConstruct;

@Component
public class UserSessionStore {

    private String awsId;

    private String awsKey;

    private String region;

    private String currentS3Bucket;

    private AmazonS3 s3Client;

    public UserSessionStore() {

    }

    public String getCurrentS3Bucket() {
        return currentS3Bucket;
    }

    public void setCurrentS3Bucket(String currentS3Bucket) {
        this.currentS3Bucket = currentS3Bucket;
    }

    public void setCredentials(String awsId, String awsKey, String region) {
        this.awsId = awsId;
        this.awsKey = awsKey;
        this.region = region;
        builds3Client();
    }

    public AmazonS3 getS3client() throws Exception {
        if(StringUtils.isEmpty(this.awsId) || StringUtils.isEmpty(this.awsKey) || StringUtils.isEmpty(this.region)){
            throw new Exception("AWS Credentials not set in session");
        }
        if(this.s3Client==null){
            builds3Client();
        }
        return s3Client;
    }

    private void builds3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(this.awsId, this.awsKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(this.region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        this.s3Client=s3Client;
    }
}
