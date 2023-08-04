package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IEditBookView;
import org.helmo.gbeditor.presenter.interfaceview.ILoginView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToEditBook;

public class EditBookPresenter {
    private IEditBookView view;
    private Repository repo;
    public EditBookPresenter(Repository repo) {
        this.repo = repo;
    }
    public void setView(IEditBookView view) {
        this.view = view;
    }

    public void editBook(String title, String resume, NavigationArg args) {
        if (title.isBlank() || resume.isBlank()) {
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

        try {
            var newBook = new Book(title, resume, args.getBook().getIsbn(), args.getBook().getAutorMatricule());
            repo.editBook(args.getBook(),newBook);
            view.goTo(ViewType.SEE_ALL_BOOKS, new NavigationArg(args.getAutor(),null,null));
        } catch (UnableToEditBook e) {
            view.throwAlert("Impossible de modifier le livre");
        } catch (UnableToConnect e) {
            view.throwAlert("Impossible de se connecter à la base de données");
        }

    }
}
