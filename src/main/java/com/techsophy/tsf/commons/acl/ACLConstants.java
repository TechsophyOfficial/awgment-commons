package com.techsophy.tsf.commons.acl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ACLConstants
{
    @Getter
    private static final String ACCOUNTS_ACL_URL = "/accounts/v1/acl/";
    @Getter
    private static final String ALLOW="allow";
    @Getter
    private static final String DENY="deny";
}
