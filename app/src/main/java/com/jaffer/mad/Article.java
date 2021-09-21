package com.jaffer.mad;

public class Article {
    private String imageUrl;
    private String articleTitle;
    private String author;
    private String description;
    private String clapCount;
    private String url;

    public Article() {
    }

    public Article(String imageUrl, String articleTitle, String author, String description, String clapCount, String url) {
        this.imageUrl = imageUrl;
        this.articleTitle = articleTitle;
        this.author = author;
        this.description = description;
        this.clapCount = clapCount;
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getClapCount() {
        return clapCount;
    }

    public String getUrl() {
        return url;
    }
}
