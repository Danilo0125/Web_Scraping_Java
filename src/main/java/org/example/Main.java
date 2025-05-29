package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        List<String> newspaperUrls = Arrays.asList(
                "https://www.lostiempos.com.bo"
        );

        try {
            NewsScraper scraper = new NewsScraper();
            CsvExporter exporter = new CsvExporter();
            List<NewsArticle> allArticles = new ArrayList<>();

            for (String url : newspaperUrls) {
                logger.info("Procesando periodico: {}", url);
                List<NewsArticle> articles = scraper.scrapeWebsite(url);
                allArticles.addAll(articles);
                logger.info("Se encontraron {} artículos en {}", articles.size(), url);
            }

            //exportar a un csv
            if (!allArticles.isEmpty()) {
                String csvFilePath = exporter.exportToCsv(allArticles, null);
                logger.info("Total de artículos exportados: {}", allArticles.size());
                logger.info("Los artículos han sido guardados en: {}", csvFilePath);
                System.out.println("Se han extraído " + allArticles.size() + " titulares y guardado en: " + csvFilePath);
            } else {
                logger.warn("No se encontro artículos para exportar");
                System.out.println("No se encontro titulares para exportar.");
            }

        } catch (Exception e) {
            logger.error("Error al procesar los periódicos: {}", e.getMessage(), e);
            System.err.println("Error en el proceso de scraping: " + e.getMessage());
        }
    }
}