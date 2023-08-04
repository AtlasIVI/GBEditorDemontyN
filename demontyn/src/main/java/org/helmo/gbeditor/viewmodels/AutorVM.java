package org.helmo.gbeditor.viewmodels;

import org.helmo.gbeditor.models.Autor;

public class AutorVM {
    private final Autor autor;

    public AutorVM(Autor autor) {
        this.autor = autor;
    }
    public String getMatricule(){
        return autor.getMatricule();
    }
    public String getCompletName(){
        return autor.getCompletName();
    }
    public String getFirstName(){
        return autor.getFirstname();
    }
    public String getLastName(){
        return autor.getName();
    }
}
