package com.jvcdp.aws.s3.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.jvcdp.aws.s3.services.S3Repository;
 
@Service
public class S3RepositoryImpl implements S3Repository {
	
	private Logger logger = LoggerFactory.getLogger(S3RepositoryImpl.class);

	@Value("${app.aws.access_key_id}")
	private String awsId;

	@Value("${app.aws.secret_access_key}")
	private String awsKey;

	@Value("${app.s3.region}")
	private String region;

	@Value("${app.s3.bucket}")
	private String apps3Bucket;

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public AmazonS3 s3client() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.build();

		return s3Client;
	}

 
	@Override
	public ByteArrayOutputStream readObject(String bucketName, String keyName) {
		try {
            S3Object s3object = s3client().getObject(new GetObjectRequest(bucketName, keyName));
            
            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, len);
            }
            
            return baos;
		} catch (IOException ioe) {
			logger.error(ioe.toString());
        } catch (AmazonServiceException ase) {
        	logger.error(ase.toString());
			throw ase;
        } catch (AmazonClientException ace) {
        	logger.error(ace.toString());
            throw ace;
        }
		
		return null;
	}
 
	@Override
	public void addObject(String bucketName, String keyName, MultipartFile file) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			s3client().putObject(bucketName, keyName, file.getInputStream(), metadata);
		} catch(IOException ioe) {
			logger.error(ioe.toString());
		} catch (AmazonServiceException ase) {
			logger.error(ase.toString());
			throw ase;
        } catch (AmazonClientException ace) {
            logger.error(ace.toString());
            throw ace;
        }
	}

	@Override
	public void deleteObject(String bucketName, String keyName) {
		try {
			s3client().deleteObject(bucketName, keyName);
		} catch (AmazonServiceException ase) {
			logger.error(ase.toString());
			throw ase;
		} catch (AmazonClientException ace) {
			logger.error(ace.toString());
			throw ace;
		}
	}

	@Override
	public List<String> listObjects(String bucketName){
		ListObjectsRequest listObjectsRequest =
				new ListObjectsRequest()
						.withBucketName(bucketName);

		List<String> keys = new ArrayList<>();

		ObjectListing objects = s3client().listObjects(listObjectsRequest);

		while (true) {
			List<S3ObjectSummary> summaries = objects.getObjectSummaries();
			if (summaries.size() < 1) {
				break;
			}

			for (S3ObjectSummary item : summaries) {
				if (!item.getKey().endsWith("/"))
					keys.add(item.getKey());
			}

			objects = s3client().listNextBatchOfObjects(objects);
		}
		return keys;
	}

	@Override
	public List<String> listObjects() {
		return this.listObjects(this.apps3Bucket);
	}

	@Override
	public ByteArrayOutputStream readObject(String keyName) {
		return this.readObject(this.apps3Bucket,keyName);
	}

	@Override
	public void addObject(String keyName, MultipartFile file) {
		this.addObject(this.apps3Bucket,keyName,file);
	}

	@Override
	public void deleteObject(String keyName) {
		this.deleteObject(apps3Bucket,keyName);
	}


}
