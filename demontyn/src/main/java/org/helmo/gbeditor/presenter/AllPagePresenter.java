package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.models.exceptions.ModelException;
import org.helmo.gbeditor.models.exceptions.UnableToAddPage;
import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IAllPageView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToCreatePage;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllPages;
import org.helmo.gbeditor.repository.exceptions.UnableToUpdatePage;
import org.helmo.gbeditor.viewmodels.BookVM;
import org.helmo.gbeditor.viewmodels.PageVM;

import java.util.List;
import java.util.stream.Collectors;

public class AllPagePresenter {
    private IAllPageView view;

    private Repository repo;
    private Book currentBook;
    private Page lastPageAdd;


    public AllPagePresenter(Repository repo) {
        this.repo = repo;
    }

    public void setView(IAllPageView view) {
        this.view = view;
    }

    public List<PageVM> getAllPages(Book book) {
        try {
            currentBook = repo.addPagesToBook(book);
            //Convertir en PageVM
            return currentBook.getPages().stream().map(PageVM::new).collect(Collectors.toList());
        } catch (UnableToGetAllPages e) {
            view.throwAlert("Unable to get all pages");
        } catch (ModelException e) {
            view.throwAlert(e.getMessage());
        }
        return null;
    }

    public void updatePage(PageVM pageVM, String text) {
        try {
            var page = new Page(pageVM.getTextPage(), pageVM.getNumberPage());
            repo.updatePage(page, text);
            view.throwAlert("Page updated");
        } catch (UnableToUpdatePage e) {
            view.throwAlert("Unable to update page");
        } catch (UnableToConnect e) {
            view.throwAlert("Unable to connect to the server");
        } catch (ModelException e) {
            view.throwAlert(e.getMessage());
        }
    }

    public void createPage(String text) {
        try {
            currentBook.addPage(new Page(text,1));
            this.lastPageAdd = currentBook.getPages().get(currentBook.getPages().size()-1);
            repo.updatePageBook(currentBook);
            view.throwAlert("Page created");
        } catch (ModelException e) {
            view.throwAlert(e.getMessage());
        }
    }

    public PageVM getLastPage() {
        return new PageVM(lastPageAdd);
    }
}
