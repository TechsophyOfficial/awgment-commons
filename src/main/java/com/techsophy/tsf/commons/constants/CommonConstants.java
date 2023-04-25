package com.techsophy.tsf.commons.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {
    /*LocaleConfig Constants*/
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String BASENAME_ERROR_MESSAGES = "classpath:errorMessages";
    public static final String BASENAME_MESSAGES = "classpath:messages";
    public static final Long CACHEMILLIS = 3600L;
    public static final Boolean USEDEFAULTCODEMESSAGE = true;
    public static final String ERROR= "error";
    //LoggingHandler
    public static final String CONTROLLER_CLASS_PATH = "execution(* com.techsophy.tsf.runtime.form.controller..*(..))";
    public static final String SERVICE_CLASS_PATH= "execution(* com.techsophy.tsf.runtime.form.service..*(..))";
    public static final String EXCEPTION = "ex";
    public static final String IS_INVOKED_IN_CONTROLLER= "{} () is invoked in controller ";
    public static final String IS_INVOKED_IN_SERVICE= "{} () is invoked in service ";
    public static final String EXECUTION_IS_COMPLETED_IN_CONTROLLER="{} () execution is completed  in controller";
    public static final String EXECUTION_IS_COMPLETED_IN_SERVICE="{} () execution is completed  in service";
    public static final String EXCEPTION_THROWN="An exception has been thrown in ";
    public static final String CAUSE="Cause : ";
    public static final String BRACKETS_IN_CONTROLLER="() in controller";
    public static final String BRACKETS_IN_SERVICE="() in service";

    /*TokenUtilsAndWebclientWrapperConstants*/
    public static final String PREFERED_USERNAME="preferred_username";
    public static final String EMPTY_STRING="";
    public static final String BEARER="Bearer ";
    public static final String REGEX_SPLIT="\\.";
    public static final String ISS="iss";
    public static final String URL_SEPERATOR="/";
    public static final int SEVEN=7;
    public static final int ONE=1;
    public static final String CONTENT_TYPE="Content-Type";
    public static final String APPLICATION_JSON ="application/json";
    public static final String GET="GET";
    public static final String PUT="PUT";
    public static final String DELETE="DELETE";
    public static final String POST = "POST";

    //TokenUtilsConstants
    public static final String CREATED_ON = "createdOn";
    public static final String UPDATED_ON = "updatedOn";
    public static final String DESCENDING = "desc";
    public static final String COLON = ":";
    public static final String CREATED_ON_ASC = "createdOn: ASC";

    public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_ZONE = "UTC";
    public static final String AUTHORIZATION="Authorization";
    public static final String SERVICE = "service";
    //JWTRoleConverter
    public static final String CLIENT_ROLES="clientRoles";
    public static final String USER_INFO_URL= "/protocol/openid-connect/userinfo";
    public static final String AWGMENT_ROLES_MISSING_IN_CLIENT_ROLES ="AwgmentRoles are missing in clientRoles";
    public static final String CLIENT_ROLES_MISSING_IN_USER_INFORMATION="ClientRoles are missing in the userInformation";
    public static final String GATEWAY_URL = "${gateway.uri}";
    public static final String RUNTIME_FORM = "tp-app-runtime-form";
    public static final String RUNTIME_FORM_MODELER_API_VERSION_1 = "Runtime Form API v1.0";
    public static final String VERSION_1 = "1.0";
    public static final String PACKAGE_NAME = "com.techsophy.tsf.runtime.*";
    public static final String MULTI_TENANCY_PACKAGE_NAME = "com.techsophy.multitenancy.mongo.*";

}
