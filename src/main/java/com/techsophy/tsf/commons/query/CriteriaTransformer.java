package com.techsophy.tsf.commons.query;

import org.springframework.data.mongodb.core.query.Criteria;

public interface CriteriaTransformer<T>
{
    T getCriteria(String field, QueryBuilder<T> builder);

}
