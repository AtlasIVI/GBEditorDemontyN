package org.helmo.gbeditor.presenter.arg;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Page;

public class NavigationArg {

    public Autor autor;
    public Book book;
    public Page page;


    public NavigationArg(Autor autor, Book book, Page page) {
        this.autor = autor;
        this.book = book;
        this.page = page;
    }
    public Autor getAutor() {
        return autor;
    }
    public Book getBook() {
        return book;
    }
    public Page getPage() {
        return page;
    }


}
