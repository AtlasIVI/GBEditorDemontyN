package org.helmo.gbeditor.viewmodels;

import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.models.exceptions.ModelException;
import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;

public class PageVM {
    private String textPage;
    private int numberPage;

    public PageVM(String textPage, int numberPage) {
        this.textPage = textPage;
        this.numberPage = numberPage;
    }
    public PageVM(Page page) {
        this.textPage = page.getTextPage();
        this.numberPage = page.getNumberPage();
    }

    //<editor-fold desc="Getters and Setters">
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

    public Page getPage() throws UnableToConstructPage {
            return new Page(textPage, numberPage);

    }
    //</editor-fold>

}
