package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IAllBooksView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllBooks;
import org.helmo.gbeditor.viewmodels.BookVM;

import java.util.List;
import java.util.stream.Collectors;

public class AllBooksPresenter {
    private IAllBooksView view;
    private Repository repo;

    private Autor autor;
    public AllBooksPresenter(Repository repo) {
        this.repo = repo;
    }
    public void setView(IAllBooksView view) {
        this.view = view;
    }
    public List<BookVM> getBooksData(NavigationArg args){
        this.autor = args.getAutor();
        try {
            var result = repo.getAllBooksFromAutor(autor.getMatricule()).stream().map(BookVM::new).collect(Collectors.toList());
            for (BookVM bookVM : result) {
                bookVM.setAutor(autor.getCompletName());
            }
            return result;
        }catch (UnableToGetAllBooks e) {
            view.throwAlert("Unable to get all books");
        }catch (UnableToConnect e) {
            view.throwAlert("Unable to connect to the server");
            view.goTo(ViewType.LOGIN,args);
        }
        return null;
    }

}
