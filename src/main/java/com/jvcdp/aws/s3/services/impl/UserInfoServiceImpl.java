package com.jvcdp.aws.s3.services.impl;

import com.jvcdp.aws.s3.model.UserInfo;
import com.jvcdp.aws.s3.services.UserInfoService;
import com.jvcdp.aws.s3.services.Utility;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

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
    public UserInfo addUser(UserInfo userDetails) {
        userDetails.setId(new Long(_lstUsrs.size()));
        _lstUsrs.add(userDetails);
        return userDetails;
    }

    @Override
    public boolean authenticate(String emailAddress, String password) {
        UserInfo existing = this.findByEmail(emailAddress);
        if(existing!=null) {
            String salt =existing.getSalt();
            String hash = Utility.md5Hash(password, salt);
            if(hash==existing.getPasswordHash())
            {
                DateTime now = new DateTime();
                //existing.setLastLogin(now);
                return true;
            }
        }
        return false;
    }
}
