package com.esi.samplereport.services.fcl;

import com.esi.samplereport.CommonUtils;
import com.esi.samplereport.dto.JasperObject;
import com.esi.samplereport.dto.ShipmentReportData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class GenerateReportServices {

    private final JasperHelper jasperHelper;

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
        JasperPrint jasperPrint = jasperHelper.compileReportsAndThenPrint(data.getTypeOfDocument(), sourceData, data.getIsFcrRequired());
        String outputLocation = jasperHelper.exportPdfFromTemplate(jasperPrint, documentOutputPath + "/" + fileName);
        return fileName;
    }


    public String generateSpecimenPreviewReport(ShipmentReportData data, String preview, String documentType) {
        String previewUrl = documentOutputPath + "/" + preview;
        String fileName = CommonUtils.generateFileName(data.getReferenceNumber(), documentType, "specimen_preview") + ".pdf";
        jasperHelper.createSpecimenPreview(previewUrl, documentPreviewOutputPath + "/" + fileName, data.getTypeOfDocument());
        return documentPreviewOutputHost+"/"+fileName;
    }

}
