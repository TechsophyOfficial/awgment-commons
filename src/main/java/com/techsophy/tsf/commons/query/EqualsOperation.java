package com.techsophy.tsf.commons.query;

import lombok.Data;

@Data
public class EqualsOperation implements FilterOperation
{
    private Object equals;

    @Override
    public <T> T getCriteria(String field, QueryBuilder<T> builder) {
        return builder.equalsQuery(field,this);
    }
}
