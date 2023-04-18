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
    public <T> T getCriteria(String field, QueryBuilder<T> builder) {

        return builder.comparatorQuery(field, this );
    }
}
