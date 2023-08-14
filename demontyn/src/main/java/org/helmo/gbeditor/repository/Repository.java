package org.helmo.gbeditor.repository;


import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Link;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.models.exceptions.UnableToAddPage;
import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;
import org.helmo.gbeditor.repository.exceptions.*;

import java.util.List;

public interface Repository {

    List<Book> getAllBooksFromAutor(String id_Autor) throws UnableToGetAllBooks, UnableToConnect;

    void editBook(Book oldBook, Book newBook) throws UnableToEditBook, UnableToConnect;

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

    /**
     *
     * @param firstname
     * @param lastname
     * @return l'auteur correspondant aux noms et prénoms donnés
     * @throws UnableToGetAutor
     * @throws UnableToConnect
     */
    Autor getAutorByNames(String firstname, String lastname) throws UnableToGetAutor, UnableToConnect;

    void deleteBook(Book currentBook);

    void saveBook(Book book) throws UnableToConnect, UnableToSaveBook;


    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des pages">

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des pages">
    List<Page> getPagesExceptArg(Book book, int pageNumber) throws UnableToConnect, UnableToGetPages, UnableToConstructPage, UnableToGetPageId;

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des pages">

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des pages">

    /**
     * Cette méthode retourne un livre  remplis avec ses pages
     * @param book
     * @return
     * @throws UnableToGetAllPages
     */
    Book addPagesToBook(Book book) throws UnableToGetAllPages, UnableToConstructPage, UnableToAddPage;

    void updatePage(Page page, String text) throws UnableToUpdatePage, UnableToConnect;

    void createPage(String text, Book book) throws UnableToUpdatePage, UnableToConnect,UnableToCreatePage;

    void updatePageBook(Book currentBook);

    void addNewLink(Page currentPage, Page nextPage, String text,Book book) throws UnableToGetPageId, UnableToConnect, UnableToAddLink;

    List<Link> getAllLinkOfPage(Book book, Page page) throws UnableToGetAllLinks, UnableToConstructLink, UnableToGetPageId, UnableToConnect;

    void updateAllLink(List<Link> listLink, Book book);

    int getBookComplete(Book book) throws UnableToGetBook, UnableToConnect;

    void publishBook(Book book) throws UnableToGetBook, UnableToConnect;

    void unPublishBook(Book book) throws UnableToGetBook, UnableToConnect;
}

