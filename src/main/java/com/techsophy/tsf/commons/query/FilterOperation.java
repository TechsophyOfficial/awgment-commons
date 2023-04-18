package com.techsophy.tsf.commons.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.mongodb.core.query.Criteria;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@JsonSubTypes.Type(value = EqualsOperation.class),
        @JsonSubTypes.Type(value = ComparatorOperation.class),
        @JsonSubTypes.Type(value = InOperation.class),
        @JsonSubTypes.Type(value = LikeOperation.class)})
public interface FilterOperation<T> extends CriteriaTransformer<T>
{

}
