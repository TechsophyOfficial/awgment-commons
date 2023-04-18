package com.techsophy.tsf.commons.query;

import lombok.Data;

@Data
public class EqualsOperation implements FilterOperation
{
    private Object equals;

    @Override
    public Object getCriteria(String field, QueryBuilder builder) {
        return builder.equalsQuery(field,this);
    }
}
