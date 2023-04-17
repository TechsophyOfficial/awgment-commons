package com.techsophy.tsf.commons.query;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

@Data
public class LikeOperation implements FilterOperation
{
    String like;

//    @Override
    public Criteria getCriteria(String field)
    {
        return Criteria.where(field).regex(this.like);
    }

    @Override
    public Object getCriteria(String field, QueryBuilder builder) {
        return builder.likeQuery(field,this);
    }
}
