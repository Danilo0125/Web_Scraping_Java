package org.example;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CsvExporter {
    private static final Logger logger = LogManager.getLogger(CsvExporter.class);

    public String exportToCsv(List<NewsArticle> articles, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HHmmss");
            fileName = "noticias_" + dateFormat.format(new Date()) + ".csv";
        }

        File outputDir = new File("output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String filePath = "output" + File.separator + fileName;

        try {
            try (OutputStream out = new FileOutputStream(filePath)) {
                byte[] bom = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
                out.write(bom);
            }
            try (CSVWriter writer = new CSVWriter(
                    new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8),
                    ';',
                    '"',
                    '\\',
                    "\n"
            )) {
                String[] header = {"Titulo", "URL"};
                writer.writeNext(header);

                for (NewsArticle article : articles) {
                    String title = article.getTitle().replace("\"", "\"\"");
                    String url = article.getUrl().replace("\"", "\"\"");
                    String[] data = {title, url};
                    writer.writeNext(data, false);
                }
            }
            logger.info("Archivo CSV generado: {}", filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Error al generar archivo CSV: {}", e.getMessage(), e);
            return null;
        }
    }
}