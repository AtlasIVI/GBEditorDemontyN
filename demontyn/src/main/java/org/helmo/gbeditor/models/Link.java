package org.helmo.gbeditor.models;

import org.helmo.gbeditor.viewmodels.LinkVM;

public class Link {
    private int idCurrentPage;
    private int idNextPage;
    private String linkContent;

    public Link(int idCurrentPage, int idNextPage, String linkContent) {
        this.idCurrentPage = idCurrentPage;
        this.idNextPage = idNextPage;
        this.linkContent = linkContent;
    }
    public Link(LinkVM linkVM) {
        this.idNextPage = linkVM.getIdNextPage();
        this.linkContent = linkVM.getLinkContent();
        this.idCurrentPage = linkVM.getIdCurrentPage();
    }

    public int getIdCurrentPage() {
        return idCurrentPage;
    }

    public void setIdCurrentPage(int idCurrentPage) {
        this.idCurrentPage = idCurrentPage;
    }

    public int getIdNextPage() {
        return idNextPage;
    }

    public void setIdNextPage(int idNextPage) {
        this.idNextPage = idNextPage;
    }

    public String getLinkContent() {
        return linkContent;
    }

    public void setLinkContent(String linkContent) {
        this.linkContent = linkContent;
    }
}
