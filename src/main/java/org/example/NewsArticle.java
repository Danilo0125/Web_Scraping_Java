package org.example;

public class NewsArticle {
    private String title;
    private String url;

    public NewsArticle(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
