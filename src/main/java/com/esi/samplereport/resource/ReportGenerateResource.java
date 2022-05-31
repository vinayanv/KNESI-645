package com.esi.samplereport.resource;

import com.esi.samplereport.dto.ShipmentReportData;
import com.esi.samplereport.services.fcl.GenerateReportServices;
import com.esi.samplereport.services.lcl.GenerateReportServicesForLCL;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fcl")
@Slf4j
@RequiredArgsConstructor
public class ReportGenerateResource {

    private final GenerateReportServices generateReportServices;


    @PostMapping("/generate-specimen")
    public ResponseEntity<String> generateSpecimenDocument(@RequestBody ShipmentReportData data, @RequestParam("documentType")
            String documentType)
    {
        try {
            String url = generateReportServices.generateSpecimenDocument(data, documentType);
            return new ResponseEntity<>(url,HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/generate-preview")
    public ResponseEntity<String> generatePreviewForFCL(@RequestBody ShipmentReportData data, @RequestParam("documentType")
            String documentType, @RequestParam("specimenFileName") String specimenReportFileName)
    {
        try {
            String url = generateReportServices.generateSpecimenPreviewReport(data,specimenReportFileName, documentType);
            return new ResponseEntity<>(url,HttpStatus.OK);
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
