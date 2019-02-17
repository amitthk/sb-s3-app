package com.jvcdp.aws.s3.services.impl;

import com.amazonaws.services.devicefarm.model.ArgumentException;
import com.jvcdp.aws.s3.model.UserInfo;
import com.jvcdp.aws.s3.services.UserInfoService;
import com.jvcdp.aws.s3.services.Utility;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByEmail(String emailAddress) throws Exception {
        List<UserInfo> lstUsr = userInfoRepository.findByEmail(emailAddress);
        if((lstUsr!=null)&&(lstUsr.size()>0)) {
            return lstUsr.get(0);
        } else {
            throw new Exception("There is no such user");
        }
    }

    @Override
    public boolean addUser(String emailAddress, String password) throws Exception {
        try{

            try{
                if(null!=this.findByEmail((emailAddress))){
                    throw new ArgumentException("There is an account with that email address");
                }
            }catch (Exception exc){
                //The user does not exist so do nothing, we will add the user
            }

            UserInfo newUser= new UserInfo();
            newUser.setUserName(emailAddress);
            newUser.setEmail(emailAddress);
            newUser.setLastLogin(LocalDateTime.now().toString());

            //Password hashing
            String salt =Utility.getRandomHash();
            newUser.setPasswordHash(Utility.md5Hash(password, salt));
            newUser.setSalt(salt);
            userInfoRepository.saveAndFlush(newUser);
            return true;

        }catch (Exception exc){
            throw exc;
        }
    }

    @Override
    public boolean authenticate(String emailAddress, String password) {
        try{
            UserInfo existing = this.findByEmail(emailAddress);
            if(existing!=null) {
                String salt =existing.getSalt();
                String hash = Utility.md5Hash(password, salt);
                if(hash.equals(existing.getPasswordHash()))
                {
                    DateTime now = new DateTime();
                    //existing.setLastLogin(now);
                    return true;
                }
            }
        }catch (Exception exc){

        }
        return false;
    }
}
