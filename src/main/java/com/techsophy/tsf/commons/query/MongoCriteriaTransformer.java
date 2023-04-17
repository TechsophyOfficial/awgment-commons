package com.techsophy.tsf.commons.query;

import org.springframework.data.mongodb.core.query.Criteria;

public interface MongoCriteriaTransformer
{
    Criteria getCriteria(String field);

}
