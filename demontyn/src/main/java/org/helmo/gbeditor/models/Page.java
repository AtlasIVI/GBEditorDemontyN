package org.helmo.gbeditor.models;

import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;

public class Page {

    private String textPage;
    private int numberPage;

    public Page(String textPage, int numberPage) throws  UnableToConstructPage {
        if(textPage == null || textPage.isEmpty())
            throw new UnableToConstructPage("textPage can't be null or empty");
        if(textPage.length() > 1499)
            throw new UnableToConstructPage("textPage can't be more than 1499 characters");
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

}
