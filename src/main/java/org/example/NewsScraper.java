package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsScraper {
    private static final Logger logger = LogManager.getLogger(NewsScraper.class);
    private static final int TIMEOUT_MS = 10000; // 10 seg de tiempo limite de respuesta
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

    public List<NewsArticle> scrapeWebsite(String url) {
        List<NewsArticle> newsArticles = new ArrayList<>();

        try {
            logger.info("Iniciando scraping de: {}", url);
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .timeout(TIMEOUT_MS)
                    .get();

            if (url.contains("opinion.com.bo")) {
                newsArticles.addAll(scrapeOpinionBolivia(doc, url));
            } else {
                logger.warn("Sitio web no soportado: {}", url);
            }

            logger.info("Scraping completado. Se encontraron {} artículos", newsArticles.size());
        } catch (IOException e) {
            logger.error("Error al conectar con el sitio web: {}", url, e);
        } catch (Exception e) {
            logger.error("Error inesperado durante el scraping: {}", e.getMessage(), e);
        }

        return newsArticles;
    }

    private List<NewsArticle> scrapeOpinionBolivia(Document doc, String baseUrl) {
        List<NewsArticle> articles = new ArrayList<>();
        try {
            Elements headlines = doc.select("h2 a");
            logger.info("Encontrados {} títulos principales en Opinion", headlines.size());

            if (headlines.isEmpty()) {
                headlines = doc.select(
                        ".carousel-item h2 a, " +
                                ".slider-item h2 a, " +
                                ".main-content > .row:first-child h2 a"
                );
            }

            int total = headlines.size();

            if (total < 3) {
                logger.warn("No hay suficientes artículos para extraer tres sin incluir el primero y el último.");
                return articles;
            }

            for (int i = 1; i < total - 1 && articles.size() < 3; i++) {
                Element headline = headlines.get(i);
                String title = headline.text().trim();
                String articleUrl = headline.absUrl("href");

                if (!title.isEmpty() && !articleUrl.isEmpty()) {
                    logger.info("Título incluido: {}", title);
                    articles.add(new NewsArticle(title, articleUrl));
                }
            }

        } catch (Exception e) {
            logger.error("Error procesando Opinion Bolivia: {}", e.getMessage(), e);
        }
        logger.info("Extraídos {} artículos principales de Opinion Bolivia", articles.size());
        return articles;
    }
}
