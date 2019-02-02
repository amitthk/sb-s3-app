package com.jvcdp.aws.s3.services;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3Repository {
	public List<String> listObjects();
	public ByteArrayOutputStream readObject(String keyName);
	public void addObject(String keyName, MultipartFile file);
	public void deleteObject(String keyName);

	public List<String> listObjects(String bucketName);
	public ByteArrayOutputStream readObject(String bucketName, String keyName);
	public void addObject(String bucketName, String keyName, MultipartFile file);
	public void deleteObject(String bucketName, String keyName);
}