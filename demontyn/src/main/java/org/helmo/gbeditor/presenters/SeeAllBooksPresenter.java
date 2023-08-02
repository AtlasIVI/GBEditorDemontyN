package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.presenters.interfaceview.ISeeAllBooksDisplay;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.views.SeeAllBooksDisplay;
import org.helmo.gbeditor.views.ViewsNavigator;
import org.helmo.gbeditor.views.interfacepresenter.ISeeAllBooksPresenter;

import java.util.List;

public class SeeAllBooksPresenter implements ISeeAllBooksPresenter {

    private final ViewsNavigator viewsNavigator;
    private final RepositoryInterface repository;
    private final ISeeAllBooksDisplay seeAllBooksDisplay;
    private final Autor autor;

    public SeeAllBooksPresenter(SeeAllBooksDisplay seeAllBooksDisplay, ViewsNavigator viewsNavigator, RepositoryInterface repository, Autor autor) {
        this.viewsNavigator = viewsNavigator;
        this.repository = repository;
        this.seeAllBooksDisplay = seeAllBooksDisplay;
        this.autor = autor;
        seeAllBooksDisplay.setSeeAllBooksPresenterInterface(this);
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return repository.getAllBooksFromAutor(Integer.parseInt(autor.getMatricule()));
        } catch (Exception e) {
            seeAllBooksDisplay.displayError(e.getMessage());
            return null;
        }
    }

    @Override
    public String getAutorName() {
        return autor.getCompletName();
    }


}
