package com.jvcdp.aws.s3;

import com.jvcdp.aws.s3.services.impl.UserSessionStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication
public class SbS3Application{
	public static void main(String[] args) {
		SpringApplication.run(SbS3Application.class, args);
	}

}