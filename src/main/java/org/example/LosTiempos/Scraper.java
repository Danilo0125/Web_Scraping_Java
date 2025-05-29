package org.example.LosTiempos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
    private final String baseUrl;
    private final List<ArticleSimple> articles;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    
    public Scraper(String baseUrl) {
        this.baseUrl = baseUrl;
        this.articles = new ArrayList<>();
    }
    
    public void scrapHomePage() throws IOException {
        System.out.println("Scraping de la sección 'En Portada' de Los Tiempos...");
        Document doc = Jsoup.connect(baseUrl)
                .userAgent(USER_AGENT)
                .get();
        
        // Seleccionar específicamente el div de "En Portada"
        Element portadaSection = doc.selectFirst("div.view-bloques-noticias-home.view-display-id-portada");
        
        if (portadaSection != null) {
            System.out.println("Sección 'En Portada' encontrada, extrayendo URLs y titulares...");
            Elements articleElements = portadaSection.select("div.views-row");
            extractTitlesAndUrls(articleElements);
            
            System.out.println("\nResumen de artículos encontrados:");
            for (ArticleSimple article : articles) {
                System.out.println("Título: " + article.getTitle());
                System.out.println("URL: " + article.getUrl());
                System.out.println("-------------------------------------------");
            }
            
            System.out.println("Total de artículos encontrados: " + articleElements.size());
        } else {
            System.out.println("No se encontró la sección 'En Portada'");
        }
    }
    
    private void extractTitlesAndUrls(Elements articleElements) {
        for (Element articleElement : articleElements) {
            try {
                // Extraer título - usando el selector específico para este formato
                Element titleElement = articleElement.selectFirst("div.views-field-title a");
                String title = titleElement != null ? titleElement.text() : "Sin título";
                
                // Extraer URL
                String url = titleElement != null ? titleElement.absUrl("href") : "";
                
                if (!title.equals("Sin título") && !url.isEmpty()) {
                    ArticleSimple article = new ArticleSimple(title, url);
                    articles.add(article);
                }
                
            } catch (Exception e) {
                System.err.println("Error al procesar un artículo: " + e.getMessage());
            }
        }
    }
    
    public List<ArticleSimple> getArticles() {
        return articles;
    }
}
