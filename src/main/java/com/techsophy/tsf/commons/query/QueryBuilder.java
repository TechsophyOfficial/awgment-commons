package com.techsophy.tsf.commons.query;


import java.util.List;

public interface QueryBuilder<T> {
    T equalsQuery(String key, EqualsOperation operation);
    T comparatorQuery(String key, ComparatorOperation operation);
    T inQuery(String key, InOperation operation);
    T likeQuery(String key, LikeOperation operation);
    T orQueries(List<T> queries);
    T andQueries(List<T> queries);

}
