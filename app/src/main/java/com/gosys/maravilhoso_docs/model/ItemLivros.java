package com.gosys.maravilhoso_docs.model;

public class ItemLivros {

    private String title, author, year, description;

    public ItemLivros(){}

    public ItemLivros(String title, String author, String year, String description) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.description = description;
    }

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

}
