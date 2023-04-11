package com.techsophy.tsf.commons.acl;

import lombok.Data;
import java.util.Map;

@Data
public class ACLDecision
{
    private final String decision;
    private final Map<String,Object> additionalDetails;
}
