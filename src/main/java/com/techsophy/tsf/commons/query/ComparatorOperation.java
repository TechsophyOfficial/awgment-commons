package com.techsophy.tsf.commons.query;

import lombok.Data;
@Data
public class ComparatorOperation implements FilterOperation
{
    private Number lt;
    private Number lte;
    private Number gt;
    private Number gte;


    @Override
    public Object getCriteria(String field, QueryBuilder builder) {

        return builder.comparatorQuery(field, this );
    }
}
