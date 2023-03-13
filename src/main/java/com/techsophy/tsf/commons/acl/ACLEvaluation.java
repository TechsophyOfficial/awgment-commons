package com.techsophy.tsf.commons.acl;

import java.util.Map;

public interface ACLEvaluation
{
    ACLDecision getRead(String aclId,Map<String,?> context);
    ACLDecision getRead(String aclId,String token,Map<String,?> context);
    ACLDecision getUpdate(String aclId,Map<String,?> context);

    ACLDecision getUpdate(String aclId, String token, Map<String,?> context);

    ACLDecision getDelete(String aclId,Map<String,?> context);
    ACLDecision getDelete(String aclId,String token,Map<String,?> context);
    ACLValidate evaluateACL(String aclId,String token,Map<String,?> context);
}
