package com.techsophy.tsf.commons.query;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

@Data
public class InOperation extends FilterOperation
{
    List<Object> in;

//    @Override
    public Criteria getCriteria(String field)
    {
        return Criteria.where(field).in(this.in);
    }
}
