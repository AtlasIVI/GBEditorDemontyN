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
import org.helmo.gbeditor.repository.exceptions.*;
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
        } catch (RepoException | ModelException e) {
            view.throwAlert(e.getMessage());
        }
        return null;
    }

    public void updatePage(PageVM pageVM, String text) {
        try {
            var page = new Page(pageVM.getTextPage(), pageVM.getNumberPage());
            repo.updatePage(page, text);
            view.throwAlert("Page updated");
        } catch (RepoException | ModelException e) {
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

    public void onClickManageButton(NavigationArg args, PageVM pageVM) {
        try {
            var page = pageVM.getPage();
            var newArgs = new NavigationArg(args.getAutor(),args.getBook(), page);
            view.goTo(ViewType.MANAGE_LINK, newArgs);
        } catch (ModelException e) {
            view.throwAlert(e.getMessage());
        }
    }

    public void deletePage(PageVM page) {
        var confirmDeletePage = view.throwAlertWithConfirm("Are you sure you want to delete this page ?");
        if (!confirmDeletePage) return;

        try {
            currentBook.removePage(page.getNumberPage());
            currentBook.rearangePageNumber();
            repo.updatePageBook(currentBook);
        } catch (ModelException e) {
            view.throwAlert(e.getMessage());
        }
        view.throwAlert("Page deleted");
    }

    public List<PageVM> getCurrentBookPages() {
        return currentBook.getPages().stream().map(PageVM::new).collect(Collectors.toList());
    }
}
