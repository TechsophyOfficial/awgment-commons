package com.techsophy.tsf.commons.query;

import java.util.List;
import java.util.logging.Filter;

public interface QueryBuilder<T> {
    T equalsQuery(String key, EqualsOperation operation);
    T comparatorQuery(String key, ComparatorOperation operation);
    T inQuery(String key, InOperation operation);
    T likeQuery(String key, LikeOperation operation);

    T andQueries(List<T> queries);

}
