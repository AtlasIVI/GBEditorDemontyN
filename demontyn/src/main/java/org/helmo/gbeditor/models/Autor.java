package org.helmo.gbeditor.models;

public class Autor {
    private String name;
    private String firstname;

    private String matricule;

    public Autor(String name, String firstname, String matricule) {
        this.name = name == null ? "" : name;
        this.firstname = firstname == null ? "" : firstname;
        this.matricule = matricule;
    }

}
