package com.jvcdp.aws.s3.controller;

import javax.validation.Valid;
import com.jvcdp.aws.s3.model.LoginViewModel;
import com.jvcdp.aws.s3.model.UserInfo;
import com.jvcdp.aws.s3.services.UserInfoService;
import com.jvcdp.aws.s3.services.Utility;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserInfoService userInfoService;


    @RequestMapping(value="login", method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String login(@RequestBody LoginViewModel userinfo) throws Exception{

        try{
            boolean status = userInfoService.authenticate(userinfo.getEmailAddress(),userinfo.getPassword());
            return status?"You have successfully logged in!":"Something went wrong. Please try again!";
        }
        catch (Exception exc){
            throw exc;
        }
    }

    @RequestMapping(value="register", method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String register(@Valid @RequestBody LoginViewModel login_vm) throws Exception{
        try{
            Boolean status = userInfoService.addUser(login_vm.getEmailAddress(),login_vm.getPassword());
            return status?"You are successfully registered!":"Something went wrong!";
        }catch(Exception exc){
            throw(exc);
        }
    }

}