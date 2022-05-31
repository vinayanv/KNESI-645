package com.esi.samplereport.dto;

import lombok.Data;

@Data
public class ContainerReportData {

    private String containerNumber;
    private String ediContainerType;
    private String cielUnitType;
    private String typeName;
    private String totalPackages;
    private String type;
    private String quantity;
    private String name;
    private String sealNumber;
    private String grossWeight;
    private String measurement;
    private String createdDate;
    private String active;
    private String goodsDescription;
    private String marksNumbers;
}
