package com.gosys.maravilhoso_docs.model;

public class ItemArtigos {

    private String title;
    private String author;
    private String year;
    private String description;
    private String id;
    private String link;

    public ItemArtigos() {
    }

    // Construtores
    public ItemArtigos(String title, String author, String year,
                       String description, String id, String link) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.description = description;
        this.id = id;
        this.link = link;
    }

    // Set e Get
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

}
