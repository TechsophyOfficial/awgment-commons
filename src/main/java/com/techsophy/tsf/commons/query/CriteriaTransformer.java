package com.techsophy.tsf.commons.query;

public interface CriteriaTransformer<T>
{
    T getCriteria(String field, QueryBuilder<T> builder);

}
