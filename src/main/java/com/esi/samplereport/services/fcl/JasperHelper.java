package com.esi.samplereport.services.fcl;

import com.esi.samplereport.utills.Constants;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.stereotype.Service;

import java.io.*;

@Slf4j
@Service
public class JasperHelper {
    public JasperPrint compileReportsAndThenPrint(String docType, String sourceData, Boolean isFcrRequired) throws JRException, UnsupportedEncodingException {

        JasperReport jasperReport = null;
        InputStream reportStream;

        if (isFcrRequired) {
            reportStream = getClass().getResourceAsStream("/reports/fcl/KN_ESI_FCR_MASTER.jrxml");
            jasperReport = JasperCompileManager.compileReport(reportStream);
        } else {
            if (docType.equals("BL")) {
                reportStream = getClass().getResourceAsStream("/reports/fcl/reports/KN_ESI_BL_MASTER.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportStream);
            } else if (docType.equals("FCR")) {
                reportStream = getClass().getResourceAsStream("/reports/fcl/KN_ESI_FCR_MASTER.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportStream);
            } else if (docType.equals("SWB")) {
                reportStream = getClass().getResourceAsStream("/reports/fcl/KN_ESI_SWB_MASTER.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportStream);
            } else if (docType.equals("RWB")) {
                reportStream = getClass().getResourceAsStream("/reports/fcl/KN_ESI_RWB_MASTER.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportStream);
            }
            else if (docType.equals("BL_BAAL")) {
                reportStream = getClass().getResourceAsStream("/reports/baal/BAAL_MASTER.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportStream);
            }
            else if (docType.equals("SWB_BAAL")) {
                reportStream = getClass().getResourceAsStream("/reports/baal/swb/KN_ESI_SWB_BAAL_MASTER.jrxml");
                jasperReport = JasperCompileManager.compileReport(reportStream);
            }

        }
        JsonDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(sourceData.getBytes("UTF-8")));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jsonDataSource);
        return jasperPrint;
    }

    public String exportPdfFromTemplate(JasperPrint jasperPrint, String outputLocation) throws JRException, FileNotFoundException, DocumentException {
        SimpleOutputStreamExporterOutput sme = new SimpleOutputStreamExporterOutput(outputLocation);
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(sme); //or any other out stream
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCreatingBatchModeBookmarks(true);//add this so your bookmarks work, you may set other parameters
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return outputLocation;
    }

    public String createSpecimenPreview(String specimenFileLocation, String outputLocation, String docType) {
        if ((docType.equals("BL")) || docType.equals("SWB"))
            // This method i called for Watermarking
            return addWatermarkAndCreatePreviewPDF(specimenFileLocation, outputLocation, Constants.DRAFT, "");
        else
            return addWatermarkAndCreatePreviewPDF(specimenFileLocation, outputLocation, Constants.DRAFT, Constants.NON_NEGO);
    }


    private String addWatermarkAndCreatePreviewPDF(String filepath, String outputLocation, String watermarkText1, String watermarkText2) {
        try {
            // read existing pdf
            PdfReader reader = new PdfReader(filepath);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputLocation));
            // text watermark
            Font FONT = new Font(Font.FontFamily.HELVETICA, 90, Font.BOLD, new GrayColor(0.5f));
            Phrase p = new Phrase(watermarkText1, FONT);
            Phrase p1 = new Phrase(watermarkText2, FONT);
            // properties
            PdfContentByte over;
            Rectangle pagesize;
            float x, y;
            // loop over every page
            int n = reader.getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                // get page size and position
                pagesize = reader.getPageSizeWithRotation(i);
                x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                over = stamper.getOverContent(i);
                over.saveState();
                // set transparency
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.2f);
                over.setGState(state);
                // add watermark text and image
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p1, x + 30, y - 25, 50);
                ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x - 35, y + 10, 50);
                over.restoreState();
            }
            stamper.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filepath;
    }
}
