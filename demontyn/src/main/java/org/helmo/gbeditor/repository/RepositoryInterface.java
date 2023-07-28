package org.helmo.gbeditor.repository;


import org.helmo.gbeditor.models.Autor;

import java.util.List;

public interface RepositoryInterface {
    void saveAutor(Autor autor);

    List<Autor> getAllAutor();


}
