package com.techsophy.tsf.commons;

import lombok.Data;
import java.util.Map;

@Data
public class ACLDecision
{
    String decision;
    Map<String,String> additionalDetails;
}