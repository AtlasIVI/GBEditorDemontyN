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

    @Override
    public String getISBN(String matricule) {
        var isbn = "2";
        if (Character.isLetter(matricule.charAt(0))) {
            isbn += matricule.substring(1, 7);
        } else {
            isbn += matricule.substring(0, 6);
        }
        isbn += String.format("%02d", (int) (Math.random() * 20));
        int reste = getCode(isbn);
        isbn += reste;
        isbn = isbn.charAt(0) + "-" + isbn.substring(1, 7) + "-" + isbn.substring(7, 9) + "-" + isbn.substring(9);
        return isbn;
    }

    @Override
    public void switchToEditBookView(String bookTitle, String bookResume, String matricule) {
        if (bookTitle.isBlank() || bookResume.isBlank() || matricule.isBlank()) {
            createBookDisplay.displayError("Tous les paramètres doivent être remplis");
            return;
        }
        if (bookTitle.length() > 150) {
            createBookDisplay.displayError("Le titre du livre ne peut pas dépasser les 150 caractères");
            return;
        }
        if (bookResume.length() > 500) {
            createBookDisplay.displayError("Le résumé du livre ne peut pas dépasser les 500 caractères");
            return;
        }
        if (matricule.length() < 6 || matricule.length() > 7) {
            createBookDisplay.displayError("Le matricule doit être composé de 6 ou 7 caractères");
            return;
        }
        var isbn = getISBN(matricule);
        try {
            repository.saveBook(bookTitle, bookResume, isbn, autor);
            viewsNavigator.switchToEditBookDisplay(bookTitle, bookResume, isbn, autor);
        } catch (Exception e) {
            createBookDisplay.displayError("Unable to save book");
        }
    }

    private int getCode(String isbn) {
        int somme = 0;
        for (int i = 0; i < isbn.length() - 1; i++) {
            somme += Integer.parseInt(isbn.substring(i, i + 1)) * (10 - i);
        }
        int reste = 11 - (somme % 11);
        if (reste == 10) {
            reste = 1;
        } else if (reste == 11) {
            reste = 0;
        }
        return reste;
    }

}
