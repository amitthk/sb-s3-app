package com.jvcdp.aws.s3.controller;

import com.amazonaws.services.devicefarm.model.ArgumentException;
import com.jvcdp.aws.s3.model.S3CredentialsModel;
import com.jvcdp.aws.s3.services.S3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController("/api")
public class HomeController {

    @Value("${app.s3.bucket}")
    private String apps3Bucket;

    @Autowired
    S3Repository s3Repository;

    @GetMapping("/list")
    public List<String> listBucket(@RequestParam(value = "bucketName", required = true) String bucketName) throws Exception {
        if(bucketName==null){
            throw new ArgumentException("bucketName cannot be null!");
        }

        List<String> lstRtrn= s3Repository.listObjects(bucketName);
        return(lstRtrn);
    }

    @PostMapping("/api/updatecredentials")
    public String updateCredentials(@RequestBody S3CredentialsModel s3CredentialsModel) {
        s3Repository.setCredentials(s3CredentialsModel.getAccess_key_id(),s3CredentialsModel.getSecret_access_key(),s3CredentialsModel.getRegion());
        return "Credentials successfully updated!";
    }

    @PostMapping("/api/file/upload")
    public String uploadMultipartFile(@RequestParam("keyname") String keyName, @RequestParam("uploadfile") MultipartFile file) throws Exception {
        s3Repository.addObject(this.apps3Bucket, keyName, file);
        return "Upload Successfully. -> KeyName = " + keyName;
    }

    @GetMapping("/api/file/download")
    public ResponseEntity<byte[]> downloadObject(@RequestParam(value = "keyname",required = true) String keyname) throws Exception {
        ByteArrayOutputStream downloadInputStream = s3Repository.readObject(this.apps3Bucket,keyname);

        return ResponseEntity.ok()
                .contentType(contentType(keyname))
                .header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment; filename=\"%s\"",keyname))
                .body(downloadInputStream.toByteArray());
    }

    @DeleteMapping("/api/file/delete")
    public String deleteObject(@RequestParam(value = "keyname",required = true) String keyname) throws Exception {
        s3Repository.deleteObject(this.apps3Bucket,keyname);

        return String.format("File %s deleted!",keyname);
    }

    private MediaType contentType(String keyname) {
        String[] arr = keyname.split("\\.");
        String type = arr[arr.length-1];
        switch(type) {
            case "txt": return MediaType.TEXT_PLAIN;
            case "png": return MediaType.IMAGE_PNG;
            case "jpg": return MediaType.IMAGE_JPEG;
            default: return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
