package com.techsophy.tsf.commons.query;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

@Data
public class InOperation implements FilterOperation
{
    List<Object> in;

//    @Override
    public Criteria getCriteria(String field)
    {
        return Criteria.where(field).in(this.in);
    }


    @Override
    public Object getCriteria(String field, QueryBuilder builder) {
        return builder.inQuery(field,this);
    }
}
