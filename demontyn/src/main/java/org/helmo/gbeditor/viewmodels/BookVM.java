package org.helmo.gbeditor.viewmodels;

import org.helmo.gbeditor.models.Book;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BookVM {
    private String title;
    private String resume;
    private String isbn;
    private String autor;
    private List<PageVM> pages;

    public BookVM(Book book) {
        this.title = book.getTitle();
        this.resume = book.getResume();
        this.isbn = book.getIsbn();
        this.pages = book.getPages().stream().map(PageVM::new).collect(Collectors.toList());

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

    public List<PageVM> getPages() {
        return pages;
    }
}
