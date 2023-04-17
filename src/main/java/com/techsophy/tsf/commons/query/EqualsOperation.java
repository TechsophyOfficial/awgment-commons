package com.techsophy.tsf.commons.query;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

@Data
public class EqualsOperation implements FilterOperation
{
    private Object equals;

//    @Override
    public Criteria getCriteria(String field)
    {
       return Criteria.where(field).is(this.equals);
    }

    @Override
    public Object getCriteria(String field, QueryBuilder builder) {
        return builder.equalsQuery(field,this);
    }
}
