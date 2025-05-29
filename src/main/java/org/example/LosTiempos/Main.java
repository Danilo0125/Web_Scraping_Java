package org.example.LosTiempos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.CsvExporter;
import org.example.NewsArticle;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando el proceso de scraping de Los Tiempos...");
        
        try {
            Scraper scraper = new Scraper("https://www.lostiempos.com");
            scraper.scrapHomePage();
            
            // Convertir ArticleSimple a NewsArticle
            List<ArticleSimple> simpleArticles = scraper.getArticles();
            List<NewsArticle> newsArticles = new ArrayList<>();
            
            for (ArticleSimple article : simpleArticles) {
                NewsArticle newsArticle = new NewsArticle(article.getTitle(), article.getUrl());
                newsArticles.add(newsArticle);
            }
            
            // Exportar a CSV con formato de nombre dinámico (noticias_dd_MM_yyyy_HHmmss.csv)
            if (!newsArticles.isEmpty()) {
                CsvExporter exporter = new CsvExporter();
                // Pasando null como nombre para que use el formato de fecha predeterminado
                String csvPath = exporter.exportToCsv(newsArticles, null);
                
                if (csvPath != null) {
                    System.out.println("Artículos exportados exitosamente a: " + csvPath);
                } else {
                    System.err.println("Error al exportar artículos a CSV");
                }
            } else {
                System.out.println("No hay artículos para exportar");
            }
            
            System.out.println("Proceso de scraping completado con éxito.");
        } catch (IOException e) {
            System.err.println("Error durante el proceso de scraping: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
