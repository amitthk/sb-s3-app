CREATE TABLE USER_S3_INFO(
	ID INT AUTO_INCREMENT,
	USER_ID INT,
	AWS_ID VARCHAR(255),
	AWS_KEY VARCHAR(2000),
	REGION VARCHAR(255),
	CURRENT_S3_BUCKET VARCHAR(255)
);