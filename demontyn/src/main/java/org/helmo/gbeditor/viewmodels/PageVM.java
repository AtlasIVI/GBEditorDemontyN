package org.helmo.gbeditor.viewmodels;

import org.helmo.gbeditor.models.Page;

public class PageVM {
    private String textPage;
    private int numberPage;

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
    //</editor-fold>

    public Page getPage() {
        return new Page(textPage, numberPage);
    }
}
