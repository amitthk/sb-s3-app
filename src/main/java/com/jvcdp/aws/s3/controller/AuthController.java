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
    public Boolean login(@RequestBody LoginViewModel userinfo){

        try{
            return(userInfoService.authenticate(userinfo.getEmailAddress(),userinfo.getPassword()));
        }
        catch (Exception exc){
            throw exc;
        }
    }

    @RequestMapping(value="register", method=RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfo register(@Valid @RequestBody LoginViewModel login_vm) throws Exception{
        if(null!=userInfoService.findByEmail((login_vm.getEmailAddress()))){
            throw new Exception("There is an account with that email address");
        }
        try{
            UserInfo newUser= new UserInfo();
            newUser.setUserName(login_vm.getEmailAddress());
            newUser.setEmail(login_vm.getEmailAddress());
            DateTime now = new DateTime();
            newUser.setLastLogin(now);

            //Password hashing
            String salt =Utility.getRandomHash();
            newUser.setPasswordHash(Utility.md5Hash(login_vm.getPassword(), salt));
            newUser.setSalt(salt);

            newUser= userInfoService.addUser(newUser);
            return(newUser);
        }catch(Exception exc){
            throw(exc);
        }
    }

}