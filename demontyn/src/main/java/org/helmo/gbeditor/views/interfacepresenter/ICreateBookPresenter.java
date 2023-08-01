package org.helmo.gbeditor.views.interfacepresenter;

public interface ICreateBookPresenter {

    String getAutorName();
    String getISBN(String matricule);
    void switchToEditBookView(String bookTitle, String bookResume, String matricule);
}
