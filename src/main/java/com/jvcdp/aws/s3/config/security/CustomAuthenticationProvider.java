package com.jvcdp.aws.s3.config.security;

import java.util.ArrayList;
import java.util.List;

import com.jvcdp.aws.s3.services.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Authentication auth = null;
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (userInfoService.authenticate(name,password)) {
            // use the credentials
            // and authenticate against the third-party system
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("USER"));

            ApplicationUser appUser = new ApplicationUser(name,password, true, true, true, true,grantedAuths,name);

            auth= new UsernamePasswordAuthenticationToken(appUser, password, grantedAuths);
            return(auth);
        } else {
            throw new SecurityException("The credentials you provided did not match our records");
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        //return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}