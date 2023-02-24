package com.techsophy.tsf.commons;

import lombok.Data;

@Data
public class ACLValidate
{
    String name;
    ACLDecision read;
    ACLDecision update;
    ACLDecision delete;
}
