package com.techsophy.tsf.commons.query;

import lombok.Data;
import java.util.List;

@Data
public class InOperation implements FilterOperation
{
    List<Object> in;


    @Override
    public <T> T getCriteria(String field, QueryBuilder<T> builder) {

        return builder.inQuery(field,this);
    }
}
