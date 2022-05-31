package com.esi.samplereport;

import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class CommonUtils {

    public static String generateFileName(String trackingNumber,String documentType,String suffix){
        return String.join("_",trackingNumber,documentType,suffix);
    }

    public static void mergePdfs(List<File> files, String destinationPath) throws DocumentException {
        try {
            PDFMergerUtility pdfmerger = new PDFMergerUtility();
            for (File file : files) {
                PDDocument document = Loader.loadPDF(file);
                pdfmerger.setDestinationFileName(destinationPath);
                pdfmerger.addSource(file);
                pdfmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
                document.close();
            }
        } catch (IOException e) {
            log.debug("error while merging BAAL documents");
        }
    }
}
