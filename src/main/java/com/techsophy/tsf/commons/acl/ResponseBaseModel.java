package com.techsophy.tsf.commons.acl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ResponseBaseModel<T>
{
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)  //making it optional
    private Boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)  // //making it optional
    private String message;
}
