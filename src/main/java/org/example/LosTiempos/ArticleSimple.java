package org.example.LosTiempos;

public class ArticleSimple {
    private String title;
    private String url;
    
    public ArticleSimple(String title, String url) {
        this.title = title;
        this.url = url;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getUrl() {
        return url;
    }
    
    @Override
    public String toString() {
        return "Título: " + title + "\nURL: " + url;
    }
}
