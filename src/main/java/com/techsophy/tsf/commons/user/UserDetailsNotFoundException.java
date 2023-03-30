package com.techsophy.tsf.commons.user;

public class UserDetailsNotFoundException extends RuntimeException
{
    final String errorCode;
    final String message;
    public UserDetailsNotFoundException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
        this.message=message;
    }
}
