package com.techsophy.tsf.commons.user;

import lombok.Data;
import java.util.Map;

@Data
public class UserFormDataDefinition
{
    private Map<String,Object> userData;
    private String userId;
    private String version;
    private String createdById;
    private String updatedById;
    private String createdOn;
    private String updatedOn;

    public UserData getUserDetails()
    {
      return new UserData(getUserData());
    }
}
