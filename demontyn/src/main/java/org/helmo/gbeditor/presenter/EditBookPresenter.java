package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.presenter.interfaceview.IEditBookView;
import org.helmo.gbeditor.presenter.interfaceview.ILoginView;
import org.helmo.gbeditor.repository.Repository;

public class EditBookPresenter {
    private IEditBookView view;
    private Repository repo;
    public EditBookPresenter(Repository repo) {
        this.repo = repo;
    }
    public void setView(IEditBookView view) {
        this.view = view;
    }
}
