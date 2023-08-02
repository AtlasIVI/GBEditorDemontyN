package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenters.interfaceview.ILoginDisplay;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllAutors;
import org.helmo.gbeditor.repository.exceptions.UnableToSaveAutor;
import org.helmo.gbeditor.views.ViewsNavigator;
import org.helmo.gbeditor.views.interfacepresenter.ILoginPresenter;

public class LoginPresenter implements ILoginPresenter {
    private final ViewsNavigator viewsNavigator;
    private final RepositoryInterface repository;
    private final ILoginDisplay loginDisplay;
    public LoginPresenter(ILoginDisplay loginDisplay, ViewsNavigator viewsNavigator, RepositoryInterface repository) {
        this.loginDisplay = loginDisplay;
        this.viewsNavigator = viewsNavigator;
        this.repository = repository;
        loginDisplay.setLoginPresenterInterface(this);
    }


    @Override
    public void login(String firstname, String lastname){
        System.out.println();
        try{
            for (Autor autor: repository.getAllAutor()) {
                if(autor.getFirstname().equals(firstname) && autor.getName().equals(lastname)){
                    viewsNavigator.switchToSeeAllBooks(autor);
                    return;
                }
            }
            var autorId = repository.saveAutor(new Autor(firstname, lastname));
            viewsNavigator.switchToSeeAllBooks(new Autor(firstname, lastname,String.valueOf(autorId)));

        }catch (UnableToConnect e){
            loginDisplay.displayError("Unable to connect to the server");
        }catch (UnableToGetAllAutors e){
            loginDisplay.displayError("Unable to get all autors");
        }catch (UnableToSaveAutor e){
            loginDisplay.displayError("Unable to save autor");
        }
    }
}
