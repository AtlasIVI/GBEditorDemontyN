package org.helmo.gbeditor.repository;


import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.repository.exceptions.*;

import java.util.List;

public interface Repository {

    List<Book> getAllBooksFromAutor(String id_Autor) throws UnableToGetAllBooks, UnableToConnect;

    /**
     * @param autor
     * @return le matricule auto-incrémenté de l'auteur ajouté
     * @throws UnableToSaveAutor
     * @throws UnableToConnect
     */
    int saveAutor(Autor autor) throws UnableToSaveAutor, UnableToConnect;

    /**
     * @return la liste de tous les auteurs
     * @throws UnableToGetAllAutors
     * @throws UnableToConnect
     */
    List<Autor> getAllAutor() throws UnableToGetAllAutors, UnableToConnect;

    Autor getAutorByNames(String firstname, String lastname) throws UnableToGetAutor, UnableToConnect;

    void saveBook(String bookTitle, String bookResume, String isbn, Autor autor) throws UnableToConnect, UnableToSaveBook;


}

