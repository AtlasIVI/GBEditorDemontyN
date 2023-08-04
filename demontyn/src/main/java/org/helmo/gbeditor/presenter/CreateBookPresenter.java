package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ICreateBookView;
import org.helmo.gbeditor.presenter.interfaceview.ILoginView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;

public class CreateBookPresenter {
    private ICreateBookView view;
    private Repository repo;
    public CreateBookPresenter(Repository repo) {
        this.repo = repo;
    }
    public void setView(ICreateBookView view) {
        this.view = view;
    }

    public void createBook(NavigationArg args, String title, String resume, String matricule) {
        if (title.isBlank() || resume.isBlank() || matricule.isBlank()) {
            view.throwAlert("Tous les paramètres doivent être remplis");
            return;
        }
        if (title.length() > 150) {
            view.throwAlert("Le titre du livre ne peut pas dépasser les 150 caractères");
            return;
        }
        if (resume.length() > 500) {
            view.throwAlert("Le résumé du livre ne peut pas dépasser les 500 caractères");
            return;
        }
        if (matricule.length() < 6 || matricule.length() > 7) {
           view.throwAlert("Le matricule doit être composé de 6 ou 7 caractères");
            return;
        }
        var isbn = getISBN(matricule);
        try {
            var book = new Book(title, resume, isbn, args.autor.getMatricule());
            repo.saveBook(book);

            view.goTo(ViewType.SEE_ALL_BOOKS,new NavigationArg(args.autor,book,null));
        } catch (Exception e) {
            view.throwAlert("Unable to save book");
        }

    }




    private String getISBN(String matricule) {
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
