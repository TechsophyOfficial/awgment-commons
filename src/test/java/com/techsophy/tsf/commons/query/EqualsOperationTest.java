package com.techsophy.tsf.commons.query;

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
 class EqualsOperationTest {
    @InjectMocks
    EqualsOperation equalsOperation;
    @Mock
    QueryBuilder queryBuilder;

    @Test
    void getCriteriaTest()
    {
        equalsOperation.getCriteria("abc",queryBuilder);
        verify(queryBuilder,times(1)).equalsQuery(eq("abc"),any());
    }
}
