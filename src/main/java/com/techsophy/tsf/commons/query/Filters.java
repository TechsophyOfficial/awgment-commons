package com.techsophy.tsf.commons.query;

import lombok.Data;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class Filters
{


    private Map<String, FilterOperation> operations;

    <T> T buildAndQuery(QueryBuilder<T> builder){
        return builder.andQueries(
                (List<T>) operations.entrySet().stream().map(
                        entry -> entry.getValue().getCriteria(entry.getKey(), builder)
                ).filter(Objects::nonNull).collect(Collectors.toList()));

    }

}
