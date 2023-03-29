package com.techsophy.tsf.commons.acl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techsophy.tsf.commons.user.UserDetails;

public class CommonsApplication
{
	public static void main(String[] args) throws JsonProcessingException {
        //empty main method to support jar execution
        UserDetails userDetails =new UserDetails("https://api-gateway.techsophy.com/api");
        userDetails.getUserDetails();
    }
}
