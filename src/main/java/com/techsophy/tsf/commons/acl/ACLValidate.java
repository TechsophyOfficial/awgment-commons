package com.techsophy.tsf.commons.acl;

import lombok.Data;

@Data
public class ACLValidate
{
    private final String name;
    private final ACLDecision read;
    private final ACLDecision update;
    private final ACLDecision delete;
}
