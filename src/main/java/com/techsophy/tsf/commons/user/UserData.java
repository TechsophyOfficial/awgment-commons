package com.techsophy.tsf.commons.user;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class UserData
{
    public UserData(Map<String,Object> map)
    {
        this.userName= String.valueOf(map.get("userName"));
        this.firstName=String.valueOf(map.get("firstName"));
        this.lastName=String.valueOf(map.get("lastName"));
        this.mobileNumber= String.valueOf(map.get("mobileNumber"));
        this.emailId=String.valueOf(map.get("emailId"));
        this.department= String.valueOf(map.get("department"));
        this.groups= (List<String>) map.get("groups");
        this.roles= (List<String>) map.get("roles");
    }
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String mobileNumber;
    private final String emailId;
    private final String department;
    private final List<String> groups;
    private final List<String> roles;
}
