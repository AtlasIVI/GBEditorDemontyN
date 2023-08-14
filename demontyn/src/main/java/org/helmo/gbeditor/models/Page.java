package org.helmo.gbeditor.models;

import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;

public class Page {

    private String textPage;
    private int numberPage;
    private int idBook;

    public Page(String textPage, int numberPage) throws  UnableToConstructPage {
        if(textPage == null || textPage.isEmpty())
            throw new UnableToConstructPage();
        if(textPage.length() > 1499)
            throw new UnableToConstructPage();
        this.textPage = textPage;
        this.numberPage = numberPage;
    }

    public String getTextPage() {
        return textPage;
    }

    public void setTextPage(String textPage) {
        this.textPage = textPage;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public int getIdBook() {

        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }
}
