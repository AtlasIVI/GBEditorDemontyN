package org.helmo.gbeditor.models;

public class Page {

    private String textPage;
    private int numberPage;

    public Page(String textPage, int numberPage) {
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
