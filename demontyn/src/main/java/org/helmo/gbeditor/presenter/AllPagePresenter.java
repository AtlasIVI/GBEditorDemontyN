package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.presenter.interfaceview.IAllPageView;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllPages;
import org.helmo.gbeditor.viewmodels.PageVM;

import java.util.ArrayList;
import java.util.List;

public class AllPagePresenter {
    private IAllPageView view;

    private Repository repo;

    public AllPagePresenter(Repository repo) {
        this.repo = repo;
    }

    public void setView(IAllPageView view) {
        this.view = view;
    }

    public List<PageVM> getAllPages(Book book) {
        try {
            List<PageVM> pageVMList = new ArrayList<>();
            var pageList = repo.getAllPagesOfBook(book);
            //Convertir en PageVM
            for (Page page : pageList) {
                pageVMList.add(new PageVM(page));
            }
            return pageVMList;
        } catch (UnableToGetAllPages e) {
            view.throwAlert("Unable to get all pages");
        }
        return null;
    }
}
