package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.models.Link;
import org.helmo.gbeditor.models.exceptions.ModelException;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.IManageLinkView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.*;
import org.helmo.gbeditor.viewmodels.LinkVM;
import org.helmo.gbeditor.viewmodels.PageVM;

import java.util.ArrayList;
import java.util.List;

public class ManageLinkPresenter {
    private IManageLinkView view;
    private Repository repo;

    public ManageLinkPresenter(Repository repo) {
        this.repo = repo;
    }

    public void setView(IManageLinkView view) {
        this.view = view;
    }

    public List<PageVM> getAllPagesExceptArg(Book book, Page page) {
        var result = new ArrayList<PageVM>();
        try {
            var listPage = repo.getPagesExceptArg(book, page.getNumberPage());
            result = listPage.stream().map(PageVM::new).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        } catch (RepoException | ModelException e) {
            view.throwAlert(e.getMessage());
        }
        return result;
    }

    public void addNewLink(Page currentPage, PageVM nextPageVM, String text, Book book) {
        try {
            if (text.isEmpty() || text.isBlank()|| text.length()>199) {
                view.throwAlert("Text cannot be empty or longer than 200 characters");
                return;
            }
            var nextPage = new Page(nextPageVM.getTextPage(), nextPageVM.getNumberPage());
            repo.addNewLink(currentPage, nextPage, text,book);
            view.throwAlert("Link added");


        } catch (RepoException | ModelException e) {
            view.throwAlert(e.getMessage());
        }
    }

    public void onAllPageBtnClicked(NavigationArg args) {
        view.goTo(ViewType.All_PAGES, args);
    }

    public List<LinkVM> getAllLinkOfPage(Book book, Page page) {
        var result = new ArrayList<LinkVM>();
        try {
            var listLink = repo.getAllLinkOfPage(book, page);
            result = listLink.stream().map(LinkVM::new).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } catch (RepoException e) {
            view.throwAlert(e.getMessage());
        }
        return result;

    }

    public void updateAllLink(List<LinkVM> linkVMList, NavigationArg args) {
        List<Link> listLink = linkVMList.stream().map(Link::new).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        repo.updateAllLink( listLink,args.getBook());
        view.throwAlert("Link updated");
    }
}
