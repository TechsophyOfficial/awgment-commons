package com.techsophy.tsf.commons.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
public class Auditable
{
    @CreatedBy
    private String createdById;
    @CreatedDate
    private String createdOn;
    @LastModifiedBy
    private String updatedById;
    @LastModifiedDate
    private String updatedOn;
}
