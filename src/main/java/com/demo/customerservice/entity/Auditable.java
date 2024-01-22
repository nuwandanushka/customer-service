package com.demo.customerservice.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

/**
 * The type Auditable.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class Auditable {
    /**
     * The Created by.
     */
    @CreatedBy
    protected String createdBy;
    /**
     * The Creation date.
     */
    @CreatedDate
    @Temporal(TIMESTAMP)
    protected Date creationDate;
    /**
     * The Last modified by.
     */
    @LastModifiedBy
    protected String lastModifiedBy;
    /**
     * The Last modified date.
     */
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastModifiedDate;
}
