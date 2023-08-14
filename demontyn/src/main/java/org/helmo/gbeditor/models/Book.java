package org.helmo.gbeditor.models;

import org.helmo.gbeditor.models.exceptions.ModelException;
import org.helmo.gbeditor.models.exceptions.UnableToAddPage;
import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;
import org.helmo.gbeditor.viewmodels.PageVM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Book {

    private final String title;
    private final String resume;
    private final String isbn;
    private final String autorMatricule;
    private List<Page> pages;

    public Book(String title, String resume, String isbn, String autorMatricule) {
        this.title = title;
        this.resume = resume;
        this.isbn = isbn;
        this.autorMatricule = autorMatricule;
        pages = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getResume() {
        return resume;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutorMatricule() {
        return autorMatricule;
    }

    public void addPage(Page page) throws UnableToAddPage, UnableToConstructPage {
        if (page == null) {
            throw new UnableToAddPage("page can't be null");
        }
        for (Page p : pages) {
            if (p.getNumberPage() == page.getNumberPage()) {
                addPageWithGeneratedNumber(page.getTextPage());
                return;
            }
        }
        pages.add(page);
    }

    private void addPageWithGeneratedNumber(String text) throws UnableToConstructPage {
        int temp = 0;
        for (var page : pages) {
            if (temp != page.getNumberPage() - 1) {
                pages.add(new Page(text, pages.indexOf(page.getNumberPage()) + 1));
            }
            temp = page.getNumberPage();
        }
        pages.add(new Page(text,pages.size() + 1));
    }

    public List<Page> getPages() {
        return pages;
    }
    public void removePage(int number){
        pages.removeIf(page -> page.getNumberPage() == number);
    }
    public List<Integer> getPagesNumber(){
        List<Integer> result = new ArrayList<>();
        for (var page : pages) {
            result.add(page.getNumberPage());
        }
        return result;
    }

    public void rearangePageNumber() throws UnableToConstructPage {
        var tempList = List.copyOf(pages);

        pages.clear();
        for (Page page : tempList) {
            page.setNumberPage(1);
            addPageWithGeneratedNumber(page.getTextPage());
        }


    }
}
