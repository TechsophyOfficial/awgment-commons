package com.techsophy.tsf.commons.user;

import lombok.Data;
import java.util.List;

@Data
public class UserData
{
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String mobileNumber;
    private final String emailId;
    private final String department;
    private final List<String> groups;
    private final List<String> roles;
}
