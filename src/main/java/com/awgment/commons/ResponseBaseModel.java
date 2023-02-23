package com.awgment.commons;

import lombok.Data;

@Data
public class ResponseBaseModel<T>
{
    private T data;
    private boolean success;
    private String message;
}
