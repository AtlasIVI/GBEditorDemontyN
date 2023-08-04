package org.helmo.gbeditor.models;

public class Book {

    private final String title;
    private final String resume;
    private final String isbn;
    private final String autorMatricule;

    public Book(String title, String resume, String isbn, String autorMatricule) {
        this.title = title;
        this.resume = resume;
        this.isbn = isbn;
        this.autorMatricule = autorMatricule;
    }

    public String getTitle() {
        return title;
    }

    public String getResume() {
        return resume;
    }

    public String getIsbn() {
        return isbn;
    }
}
