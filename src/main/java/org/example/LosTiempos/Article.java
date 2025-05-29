package org.example.LosTiempos;

public class Article {
    private String title;
    private String url;
    private String imageUrl;
    private String summary;
    private String content;
    private String publishDate;
    private String section;
    
    public Article(String title, String url, String imageUrl, String summary) {
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.summary = summary;
        this.content = "";
        this.publishDate = "";
        this.section = "";
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getPublishDate() {
        return publishDate;
    }
    
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    
    public String getSection() {
        return section;
    }
    
    public void setSection(String section) {
        this.section = section;
    }
    
    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", section='" + section + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
