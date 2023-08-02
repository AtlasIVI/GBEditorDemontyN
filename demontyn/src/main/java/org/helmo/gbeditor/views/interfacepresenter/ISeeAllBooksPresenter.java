package org.helmo.gbeditor.views.interfacepresenter;

import org.helmo.gbeditor.models.Book;

import java.util.List;

public interface ISeeAllBooksPresenter {


    List<Book> getAllBooks();

    String getAutorName();
}
