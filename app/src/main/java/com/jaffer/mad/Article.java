package com.jaffer.mad;

public class Article {
    private String imageUrl;
    private String articleTitle;
    private String author;
    private String description;
    private String clapCount;

    public Article(String imageUrl, String articleTitle, String author, String description, String clapCount) {
        this.imageUrl = imageUrl;
        this.articleTitle = articleTitle;
        this.author = author;
        this.description = description;
        this.clapCount = clapCount;
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
}
