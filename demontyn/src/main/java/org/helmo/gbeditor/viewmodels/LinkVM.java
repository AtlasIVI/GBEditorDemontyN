package org.helmo.gbeditor.viewmodels;

import org.helmo.gbeditor.models.Link;

public class LinkVM {
    private int idCurrentPage;
    private int idNextPage;
    private String linkContent;

    public LinkVM(int idCurrentPage, int idNextPage, String linkContent) {
        this.idCurrentPage = idCurrentPage;
        this.idNextPage = idNextPage;
        this.linkContent = linkContent;
    }
    public LinkVM(Link link) {
        this.idCurrentPage = link.getIdCurrentPage();
        this.idNextPage = link.getIdNextPage();
        this.linkContent = link.getLinkContent();
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
