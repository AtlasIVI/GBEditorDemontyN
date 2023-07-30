package org.helmo.gbeditor.presenters;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenters.interfaceview.ILoginDisplay;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllAutors;
import org.helmo.gbeditor.views.interfacepresenter.LoginPresenterInterface;
import org.helmo.gbeditor.views.ViewsNavigator;

public class LoginPresenter implements LoginPresenterInterface {
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
        var autorEntry = new Autor(firstname, lastname);
        try{
            for (Autor autor: repository.getAllAutor()) {
                if(autor.getFirstname().equals(firstname) && autor.getName().equals(lastname)){
                    viewsNavigator.switchToCreateBookDisplay(autor);
                    return;
                }
            }
            repository.saveAutor(new Autor(firstname, lastname));
            viewsNavigator.switchToCreateBookDisplay(autorEntry);


        }catch (UnableToConnect e){
            loginDisplay.displayError("Unable to connect to the server");
        }catch (UnableToGetAllAutors e){
            loginDisplay.displayError("Unable to get all autors");
        }catch (Exception e){
            loginDisplay.displayError("Unable to save autor");
        }
    }
}
