package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenters.interfaceview.ICreateBookDisplay;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.views.CreateBookDisplay;
import org.helmo.gbeditor.views.interfacepresenter.ICreateBookPresenter;
import org.helmo.gbeditor.views.ViewsNavigator;

public class CreateBookPresenter implements ICreateBookPresenter {
    private final ViewsNavigator viewsNavigator;
    private final RepositoryInterface repository;
    private final ICreateBookDisplay createBookDisplay;
    public CreateBookPresenter(CreateBookDisplay createBookDisplay, ViewsNavigator viewsNavigator, RepositoryInterface repository, Autor autor) {
        this.createBookDisplay = createBookDisplay;
        this.viewsNavigator = viewsNavigator;
        this.repository = repository;
        createBookDisplay.setICreateBookPresenter(this);



    }



}
