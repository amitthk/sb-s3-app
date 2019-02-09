package com.jvcdp.aws.s3.services.impl;

import com.amazonaws.services.devicefarm.model.ArgumentException;
import com.jvcdp.aws.s3.model.UserInfo;
import com.jvcdp.aws.s3.services.UserInfoService;
import com.jvcdp.aws.s3.services.Utility;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private List<UserInfo> _lstUsrs = new ArrayList<UserInfo>();

    @Override
    public UserInfo findByEmail(String emailAddress) {
        return Utility.findByProperty(_lstUsrs,uif -> emailAddress.equalsIgnoreCase(uif.getEmail()));
    }

    @Override
    public boolean addUser(String emailAddress, String password) {
        try{

            if(null!=this.findByEmail((emailAddress))){
                throw new ArgumentException("There is an account with that email address");
            }
            UserInfo newUser= new UserInfo();
            newUser.setUserName(emailAddress);
            newUser.setEmail(emailAddress);
            newUser.setLastLogin(LocalDateTime.now().toString());

            //Password hashing
            String salt =Utility.getRandomHash();
            newUser.setPasswordHash(Utility.md5Hash(password, salt));
            newUser.setSalt(salt);
            newUser.setId(new Long(_lstUsrs.size()));
            _lstUsrs.add(newUser);
            return true;

        }catch (Exception exc){
            throw exc;
        }
    }

    @Override
    public boolean authenticate(String emailAddress, String password) {
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
        return false;
    }
}
