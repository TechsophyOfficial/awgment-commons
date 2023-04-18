package com.techsophy.tsf.commons.query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
 class ComparatorOperationTest {
    @Mock
    QueryBuilder queryBuilder;
    @InjectMocks
    ComparatorOperation comparatorOperation;

    @Test
    void getCriteriaTest()
    {
        comparatorOperation.getCriteria("abc",queryBuilder);
        verify(queryBuilder,times(1)).comparatorQuery(eq("abc"),any());
    }

}
