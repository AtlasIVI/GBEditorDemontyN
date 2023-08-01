package org.helmo.gbeditor.repository;


import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllAutors;
import org.helmo.gbeditor.repository.exceptions.UnableToSaveAutor;
import org.helmo.gbeditor.repository.exceptions.UnableToSaveBook;

import java.util.List;

public interface RepositoryInterface {

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

    void saveBook(String bookTitle, String bookResume, String isbn, Autor autor) throws UnableToConnect, UnableToSaveBook;


}

