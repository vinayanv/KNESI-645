package com.esi.samplereport.resource;

import com.esi.samplereport.dto.ShipmentReportData;
import com.esi.samplereport.services.baal.GenerateBaalReportServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baal")
public class ReportGenerateBAALResource {

    private GenerateBaalReportServices generateBaalReportServices;

    @GetMapping("/generate-specimen")
    public ResponseEntity<String> generateSpecimenDocument(@RequestBody ShipmentReportData data, @RequestParam("documentType") String documentType) {
        try {
            String url = generateBaalReportServices.generateSpecimenDocument(data, documentType);
            return new ResponseEntity<>(url, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/generate-preview")
    public ResponseEntity<String> generateSpecimenDocument(@RequestBody ShipmentReportData data, @RequestParam("documentType")
            String documentType,@RequestParam("specimenFileName") String specimenReportFileName) {
        try {
            String url = generateBaalReportServices.generateSpecimenPreviewReport(data, specimenReportFileName, documentType);
            return new ResponseEntity<>(url, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
