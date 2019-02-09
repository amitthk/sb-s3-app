package com.jvcdp.aws.s3.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.amazonaws.services.devicefarm.model.ArgumentException;
import com.jvcdp.aws.s3.model.LoginViewModel;
import com.jvcdp.aws.s3.model.UserInfo;
import com.jvcdp.aws.s3.services.UserInfoService;
import com.jvcdp.aws.s3.services.Utility;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


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
        catch (SecurityException secexc){
            throw secexc;
        }
        catch (Exception exc){
            throw exc;
        }
    }

    @ExceptionHandler(SecurityException.class)
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid Credentials!");
    }
    @ExceptionHandler(ArgumentException.class)
    void handleBadArgumentRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Arguments are invalid or there an existing id with that email!");
    }

    @RequestMapping(value="logout", method=RequestMethod.POST)
    public String logout(@RequestParam String emailAddress) throws Exception{

        try{
            //perform session cleanup here
            return "Logged Out Successfully!";
        }
        catch (SecurityException secexc){
            throw secexc;
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