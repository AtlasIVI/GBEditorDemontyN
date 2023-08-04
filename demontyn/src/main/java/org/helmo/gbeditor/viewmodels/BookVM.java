package org.helmo.gbeditor.viewmodels;

import org.helmo.gbeditor.models.Book;

public class BookVM {
    private String title;
    private String resume;
    private String isbn;
    private String autor;

    public BookVM(Book book) {
        this.title = book.getTitle();
        this.resume = book.getResume();
        this.isbn = book.getIsbn();

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
    public void setAutor(String autor) {
         this.autor = autor;
    }
    public String getAutor() {
        return autor;
    }

    public Book getBook() {
        return new Book(title, resume, isbn, autor);
    }

}
