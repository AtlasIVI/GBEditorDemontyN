package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenters.interfaceview.ICreateBookDisplay;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.views.CreateBookDisplay;
import org.helmo.gbeditor.views.interfacepresenter.ICreateBookPresenter;
import org.helmo.gbeditor.views.ViewsNavigator;

public class CreateBookPresenter implements ICreateBookPresenter {
    private final ViewsNavigator viewsNavigator;
    private final RepositoryInterface repository;
    private final ICreateBookDisplay createBookDisplay;

    private final Autor autor;

    public CreateBookPresenter(CreateBookDisplay createBookDisplay, ViewsNavigator viewsNavigator, RepositoryInterface repository, Autor autor) {
        this.createBookDisplay = createBookDisplay;
        this.viewsNavigator = viewsNavigator;
        this.repository = repository;
        this.autor = autor;
        createBookDisplay.setICreateBookPresenter(this);


    }


    @Override
    public String getAutorName() {
        return autor.getCompletName();
    }

    //TODO Faire la génération auto de l'isbn des livres
    @Override
    public String getISBN(String matricule) {
        var isbn = "2";
        if (Character.isLetter(matricule.charAt(0))) {
            isbn += matricule.substring(1, 7);
        }
        isbn += String.format("%02d", (int) (Math.random() * 20));
        var somme = 0;
        for (int i = 0; i < 9; i++) {
            somme += Integer.parseInt(isbn.substring(i, i + 1)) * (10 - i);
        }
        var modulo = somme % 11;
        modulo = 11 - modulo;
        if (modulo == 11) {
            modulo = 0;
        } else if (modulo == 10) {
            modulo = 1;
        }
        isbn += modulo;
        return isbn;
    }

    @Override
    public void switchToEditBookView(String bookTitle, String bookResume, String matricule) {
        var isbn = getISBN(matricule);
        try {
            repository.saveBook(bookTitle, bookResume, isbn, autor);
            viewsNavigator.switchToEditBookDisplay(bookTitle, bookResume, isbn, autor);
        } catch (Exception e) {
            createBookDisplay.displayError("Unable to save book");
        }

    }
}
