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
 class LikeOperationTest {
    @Mock
    QueryBuilder queryBuilder;
    @InjectMocks
    LikeOperation likeOperation;

    @Test
    void getCriteriaTest()
    {
        likeOperation.getCriteria("abc",queryBuilder);
        verify(queryBuilder,times(1)).likeQuery(eq("abc"),any());
    }

}
