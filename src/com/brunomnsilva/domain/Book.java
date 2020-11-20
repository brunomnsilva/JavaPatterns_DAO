package com.brunomnsilva.domain;

import java.io.Serializable;

/**
 * @author brunomnsilva
 */
public class Book implements Serializable {

    private final String isbn;
    private String author;
    private String title;
    private int year;

    public Book(String isbn, String author, String title, int year) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
