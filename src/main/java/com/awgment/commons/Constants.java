package com.awgment.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants
{
    public static final String CONTENT_TYPE="Content-Type";
    public static final String APPLICATION_JSON ="application/json";
    public static final String AUTHORIZATION="Authorization";
    public static final String BEARER="Bearer ";
    public static final String ACCOUNTS_ACL_URL = "/accounts/v1/acl/";
}
