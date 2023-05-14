package qaguru.seilah.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipReadTest {
    private ClassLoader classLoader = ZipReadTest.class.getClassLoader();

    private final String ZIP_NAME = "testArchive.zip";
    private final String PDF_NAME = "pdfFile.pdf";
    private final String PDF_HEADER_TEXT = "Как скачать файл с помощью Selenide";
    private final String XLSX_NAME = "xlsFile.xlsx";
    private final String XLSX_EXPECTED_TEXT = "Krishnah";
    private final String CSV_NAME = "csvFile.csv";
    private final String[] CSV_HEADER_TEXTS = new String[] {"id", "firstname", "lastname", "email", "email2", "profession"};

    @DisplayName("Check PDF file contains text \"Как скачать файл с помощью Selenide\"")
    @Test
    public void readFilePDF() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(ZIP_NAME);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(PDF_NAME)) {
                    PDF pdf = new PDF(zis);
                    System.out.println(pdf.text);
                    assertTrue(pdf.text.contains(PDF_HEADER_TEXT));
                }
            }
        }
    }

    @DisplayName("Check XLS file contains text \"Krishnah\" in first first_name column")
    @Test
    public void readFileXLS() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(ZIP_NAME);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(XLSX_NAME)) {
                    XLS xls = new XLS(zis);
                    System.out.println(xls.excel.getAllNames());
                    String actualTextFromFirstEmailRow = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    assertEquals(XLSX_EXPECTED_TEXT, actualTextFromFirstEmailRow);
                }
            }
        }
    }

    @DisplayName("Check CSV file contains correct headers")
    @Test
    public void readFileCSV() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(ZIP_NAME);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(CSV_NAME)) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertArrayEquals(CSV_HEADER_TEXTS, content.get(0));
                }
            }
        }
    }
}