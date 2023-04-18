package com.techsophy.tsf.commons.query;

import lombok.Data;

@Data
public class LikeOperation implements FilterOperation
{
    String like;

    @Override
    public Object getCriteria(String field, QueryBuilder builder) {
        return builder.likeQuery(field,this);
    }
}
