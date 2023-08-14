package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.exceptions.ModelException;
import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IAllBooksView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.RepoException;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetPageId;
import org.helmo.gbeditor.repository.exceptions.UnableToGetPages;
import org.helmo.gbeditor.viewmodels.BookVM;
import org.helmo.gbeditor.viewmodels.PageVM;

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

    public List<BookVM> getBooksData(NavigationArg args) {
        this.autor = args.getAutor();
        try {
            var result = repo.getAllBooksFromAutor(autor.getMatricule()).stream().map(BookVM::new).collect(Collectors.toList());
            for (BookVM bookVM : result) {
                bookVM.setAutor(autor.getCompletName());
            }
            return result;
        } catch (UnableToConnect e) {
            view.throwAlert("Unable to connect to the server");
            view.goTo(ViewType.LOGIN, args);
        } catch (RepoException e) {
            view.throwAlert(e.getMessage());
        }
        return null;
    }

    public void deleteBook(NavigationArg args, Book book) {
        try {
            var confirmDel = view.throwAlertWithConfirm("Are you sure you want to delete this book?");
            if (confirmDel) {
                repo.deleteBook(book);
                view.throwAlert("Book deleted");
                view.goTo(ViewType.SEE_ALL_BOOKS, args);

            }
        } catch (Exception e) {
            view.throwAlert("Unable to delete book");
        }

    }

    public boolean isBookPublished(Book book) {
        try {
            var result = repo.getBookComplete(book);
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (RepoException e) {
            view.throwAlert("Unable to connect to the server");
            return false;
        }
    }

    public void publishBook(NavigationArg args, Book book) {
        try {
            repo.publishBook(book);
            view.throwAlert("Book published");
            view.goTo(ViewType.SEE_ALL_BOOKS, args);
        } catch (Exception e) {
            view.throwAlert("Unable to publish book");
        }
    }

    public void unPublishBook(NavigationArg args, Book book) {
        try {
            repo.unPublishBook(book);
            view.throwAlert("Book unpublished");
            view.goTo(ViewType.SEE_ALL_BOOKS, args);
        } catch (Exception e) {
            view.throwAlert("Unable to publish book");
        }
    }

    public List<PageVM> getPages(NavigationArg args, Book book) {
        try {
            return repo.getPagesExceptArg(book, 0).stream().map(PageVM::new).collect(Collectors.toList());
        } catch (RepoException | ModelException e) {
            view.throwAlert(e.getMessage());
        }

        return null;
    }

}
