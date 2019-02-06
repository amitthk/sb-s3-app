package com.jvcdp.aws.s3.model;

import com.jvcdp.aws.s3.model.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginViewModel {
    @NotNull
    @ValidEmail
    private String emailAddress;

    @NotNull
    @Size(min = 8,message = "Cannot be less than 8 characters!")
    private String password;

    public LoginViewModel() {
        super();
    }

    public LoginViewModel(@NotNull String emailAddress, @NotNull String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
