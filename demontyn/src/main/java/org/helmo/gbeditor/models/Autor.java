package org.helmo.gbeditor.models;

public class Autor {

    private String name;
    private String firstname;

    private String matricule;

    public Autor(String name, String firstname) {
        this.name = name == null ? "" : name;
        this.firstname = firstname == null ? "" : firstname;
    }
    public Autor(String name, String firstname, String matricule) {
        this.name = name == null ? "" : name;
        this.firstname = firstname == null ? "" : firstname;
        this.matricule = matricule == null ? "" : matricule;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCompletName() {
        return this.name + "" + this.firstname;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }


    @Override
    public String toString() {
        return name + " " + firstname + ", Matricule :  " + String.valueOf(matricule);
    }
}
