package com.techsophy.tsf.commons.query;

import lombok.Data;

@Data
public class LikeOperation implements FilterOperation
{
    String like;

    @Override
    public <T> T getCriteria(String field, QueryBuilder<T> builder) {
        return builder.likeQuery(field,this);
    }
}
