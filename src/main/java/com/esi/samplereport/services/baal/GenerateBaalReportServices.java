package com.esi.samplereport.services.baal;

import com.esi.samplereport.CommonUtils;
import com.esi.samplereport.dto.JasperObject;
import com.esi.samplereport.dto.ShipmentReportData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class GenerateBaalReportServices {


    private JasperHelperForBAAL jasperHelperForBAAL;

    @Value("${email.attachment.doc.path}")
    private String documentOutputPath;

    @Value("${email.attachment.preview.path}")
    private String documentPreviewOutputPath;

    @Value("${email.attachment.preview.host}")
    private String documentPreviewOutputHost;

    public String generateSpecimenDocument(ShipmentReportData data, String documentType) throws JRException, UnsupportedEncodingException, JsonProcessingException, DocumentException, FileNotFoundException {
        String fileName = CommonUtils.generateFileName(data.getReferenceNumber(), documentType, "specimen") + ".pdf";
        ObjectMapper objectMapper = new ObjectMapper();
        String sourceData = objectMapper.writeValueAsString(new JasperObject(data));
        JasperPrint jasperPrint = jasperHelperForBAAL.compileReportsAndThenPrint(data.getTypeOfDocument(), sourceData, data.getIsFcrRequired());
        String outputLocation = jasperHelperForBAAL.exportPdfFromTemplate(jasperPrint, documentOutputPath + "/" + fileName);
        if(data.getTypeOfDocument().endsWith("BAAL")){
            File file1 = new File(outputLocation);
            File file2 = ResourceUtils.getFile("classpath:reports/fcl/baal/BAAL_Terms_and_Conditions.pdf");
            CommonUtils.mergePdfs(Arrays.asList(file1,file2),outputLocation);
        }
        return fileName;
    }


    public String generateSpecimenPreviewReport(ShipmentReportData data, String preview, String documentType) {
        String previewUrl = documentOutputPath + "/" + preview;
        String fileName = CommonUtils.generateFileName(data.getReferenceNumber(), documentType, "specimen_preview") + ".pdf";
        jasperHelperForBAAL.createSpecimenPreview(previewUrl, documentPreviewOutputPath + "/" + fileName, data.getTypeOfDocument());
        return documentPreviewOutputHost+"/"+fileName;
    }
}
