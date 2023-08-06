package org.helmo.gbeditor.presenter;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.presenter.arg.NavigationArg;
import org.helmo.gbeditor.presenter.interfaceview.ILoginView;
import org.helmo.gbeditor.presenter.interfaceview.ViewType;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAutor;
import org.helmo.gbeditor.repository.exceptions.UnableToSaveAutor;

public class LoginPresenter {

    private ILoginView view;

    private Repository repo;

    public LoginPresenter(Repository repo) {
        this.repo = repo;
    }

    public void setView(ILoginView view) {
        this.view = view;
    }

    public void login(String firstname, String lastname) {
        try{
            var existingAutor = repo.getAutorByNames(firstname, lastname);
            if(existingAutor != null){
                view.goTo(ViewType.SEE_ALL_BOOKS,new NavigationArg(existingAutor,null,null));
                return;
            }
            var autorId = repo.saveAutor(new Autor(lastname, firstname));
            view.goTo(ViewType.SEE_ALL_BOOKS,new NavigationArg(new Autor(lastname, firstname,String.valueOf(autorId)),null,null));

        } catch (UnableToGetAutor e) {
            view.throwAlert("Unable to get autor");
        } catch (UnableToConnect e) {
            view.throwAlert("Unable to connect to the server");
        } catch (UnableToSaveAutor e) {
            view.throwAlert("Unable to save autor");
        }
    }
}
