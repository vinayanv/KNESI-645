package com.esi.samplereport.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class Customer {

    private UUID id;
    private String name;
    private String customerCode;
    private String customerId;
    private ClientDetails clientDetails;
    private Boolean isActive;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
}
