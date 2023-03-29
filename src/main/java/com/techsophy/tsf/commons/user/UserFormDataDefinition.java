package com.techsophy.tsf.commons.user;

import lombok.Data;

@Data
public class UserFormDataDefinition
{
    private UserData userData;
    private String userId;
    private String version;
    private String createdById;
    private String updatedById;
    private String createdOn;
    private String updatedOn;
}
