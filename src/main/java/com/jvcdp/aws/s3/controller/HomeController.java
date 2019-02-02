package com.jvcdp.aws.s3.controller;

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

    @Autowired
    S3Repository s3Repository;

    @Value("${app.s3.bucket}")
    String gkzS3Bucket;

    @GetMapping("/list")
    public List<String> listBucket(@RequestParam(value = "bucketName", required = false) String bucketName){
        if(bucketName==null || bucketName.isEmpty()){
            bucketName=gkzS3Bucket;
        }

        List<String> lstRtrn= s3Repository.listObjects(bucketName);
        return(lstRtrn);
    }

    @PostMapping("/file/upload")
    public String uploadMultipartFile(@RequestParam("keyname") String keyName, @RequestParam("uploadfile") MultipartFile file) {
        s3Repository.addObject(keyName, file);
        return "Upload Successfully. -> KeyName = " + keyName;
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadObject(@RequestParam(value = "keyname",required = true) String keyname) {
        ByteArrayOutputStream downloadInputStream = s3Repository.readObject(keyname);

        return ResponseEntity.ok()
                .contentType(contentType(keyname))
                .header(HttpHeaders.CONTENT_DISPOSITION,String.format("attachment; filename=\"%s\"",keyname))
                .body(downloadInputStream.toByteArray());
    }

    @DeleteMapping("/file")
    public String deleteObject(@RequestParam(value = "keyname",required = true) String keyname) {
        s3Repository.deleteObject(keyname);

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
