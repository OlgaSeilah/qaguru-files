package qaguru.seilah;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipReadTest {
    private ClassLoader classLoader = ZipReadTest.class.getClassLoader();
    private String zipName = "testArchive.zip";
    private String pdfName = "pdfFile.pdf";
    private String textFromPdf = "Как скачать файл с помощью Selenide";
    private String xlsName = "__MACOSX/._xlsFile.xlsx";
    private String expectedText = "webinar-testzthssjwei@qa-mail.webinar.ru";
    private String csvName = "__MACOSX/._csvFile.csv";
    private String[] arrayFirstRowExpected = new String[] {"id", "firstname", "lastname", "email", "email2", "profession"};

    @DisplayName("Check PDF file contains text \"Как скачать файл с помощью Selenide\"")
    @Test
    public void readFilePDF() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream(zipName);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(pdfName)) {
                    PDF pdf = new PDF(zis);
                    System.out.println(pdf.text);
                    assertTrue(pdf.text.contains(textFromPdf));
                }
            }
        }
    }

    @Test
    public void parseXlsx() throws Exception {
        XLS xls = new XLS(new File("src/test/resources/xlsFile.xlsx"));
        assertEquals("Krishnah",
                xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue());
    }

    @DisplayName("Check XLS file contains text \"Krishnah\" in first first_name column")
    @Test
    public void readFileXLS() throws Exception {
        int i = 1;
        try (InputStream is = classLoader.getResourceAsStream(zipName);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equals(xlsName)) {
                    XLS xls = new XLS(zis);
                    System.out.println(xls.excel.getAllNames());
                    String actualTextFromFirstEmailRow = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    assertEquals(expectedText, actualTextFromFirstEmailRow);
                }
            }
        }
    }

    @DisplayName("Check CSV file ") //todo change name
    @Test
    public void readFileCSV() throws Exception {
        int i = 1;
        try (InputStream is = classLoader.getResourceAsStream(zipName);
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) { //entry.getName(csvName) находит тот же файл, и тест падает так же
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    System.out.println(content.get(0));
                    Assertions.assertArrayEquals(arrayFirstRowExpected, content.get(0));




                }
            }
        }
    }

//    @Test
//    public void readFile1() throws Exception {
//        File csv = new File("src/test/resources/csvFile.csv");
//        try (InputStream is = new FileInputStream(csv)) {
//            byte[] bytes = is.readAllBytes();
//            String csvAsString = new String(bytes, StandardCharsets.UTF_8 );
//            assertTrue(csvAsString.contains("Andree"));
//        }
//    }
//
//    @Test
//    public void parsePdf() throws Exception {
//        PDF pdf = new PDF(new File("src/test/resources/pdfFile.pdf"));
//        Assertions.assertEquals("Skia/PDF m112",
//                pdf.producer);
//    }
//

}