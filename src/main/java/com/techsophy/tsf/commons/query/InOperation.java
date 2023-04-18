package com.techsophy.tsf.commons.query;

import lombok.Data;
import java.util.List;

@Data
public class InOperation implements FilterOperation
{
    List<Object> in;


    @Override
    public Object getCriteria(String field, QueryBuilder builder) {

        return builder.inQuery(field,this);
    }
}
