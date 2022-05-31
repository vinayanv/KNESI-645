package com.esi.samplereport.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
public class ClientDetails {
    private UUID id;
    private String type;
    private String clientName;
    private String clientId;
    private Boolean isActive;
    private ZonedDateTime createdDate;
    private ZonedDateTime updatedDate;
}
