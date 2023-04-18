package com.techsophy.tsf.commons.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
 class FiltersTest {

    @Mock
    QueryBuilder queryBuilder;
    @InjectMocks
    Filters filters;

    @Test
    void buildAndQuery() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        filters= mapper.readValue("{\"operations\":{\"formData.age\":{\"in\":[\"31\",\"32\"]}}}"
                , Filters.class);
        filters.buildAndQuery(queryBuilder);
        verify(queryBuilder,times(1)).andQueries(any());
    }

}
