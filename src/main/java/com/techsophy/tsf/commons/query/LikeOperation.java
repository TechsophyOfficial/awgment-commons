package com.techsophy.tsf.commons.query;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Criteria;

@Data
public class LikeOperation extends FilterOperation
{
    String like;

//    @Override
    public Criteria getCriteria(String field)
    {
        return Criteria.where(field).regex(this.like);
    }
}
