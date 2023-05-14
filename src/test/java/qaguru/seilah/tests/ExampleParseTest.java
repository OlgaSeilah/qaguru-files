package qaguru.seilah.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleParseTest {
    @Test
    public void parseXlsx() throws Exception {
        XLS xls = new XLS(new File("src/test/resources/xlsFile.xlsx"));
        assertEquals("Krishnah",
                xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue());
    }

    @Test
    public void readFile() throws Exception {
        File csv = new File("src/test/resources/csvFile.csv");
        try (InputStream is = new FileInputStream(csv)) {
            byte[] bytes = is.readAllBytes();
            String csvAsString = new String(bytes, StandardCharsets.UTF_8 );
            assertTrue(csvAsString.contains("Andree"));
        }
    }

    @Test
    public void parsePdf() throws Exception {
        PDF pdf = new PDF(new File("src/test/resources/pdfFile.pdf"));
        assertEquals("Skia/PDF m112",
                pdf.producer);
    }
}