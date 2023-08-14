package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
import org.helmo.gbeditor.models.Link;
import org.helmo.gbeditor.models.Page;
import org.helmo.gbeditor.models.exceptions.UnableToAddPage;
import org.helmo.gbeditor.models.exceptions.UnableToConstructPage;
import org.helmo.gbeditor.repository.Repository;
import org.helmo.gbeditor.repository.exceptions.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbInfrastructure implements Repository {

    private String dbUrl;
    private String username;
    private String password;
    private String driverName;


    public DbInfrastructure(String driverName, String dbUrl, String username, String password) throws UnableToConnect {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
        this.driverName = driverName;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new UnableToConnect();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des livres">


    @Override
    public int getBookComplete(Book book) throws UnableToGetBook, UnableToConnect {
        try(var connection = getConnection()){
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT isComplete FROM Book WHERE isbn_Book = ? AND title_Book = ?");
            prepareStatement.setString(1, book.getIsbn());
            prepareStatement.setString(2, book.getTitle());
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            if(resSet.next()){
                return resSet.getInt("isComplete");
            }
        } catch (SQLException e) {
            throw new UnableToGetBook("Unable to get book");
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        }
        return  0;
    }

    public void publishBook(Book book) throws UnableToGetBook, UnableToConnect {
        try(var connection = getConnection()){
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Book SET isComplete = 1 WHERE isbn_Book = ? AND title_Book = ?");
            prepareStatement.setString(1, book.getIsbn());
            prepareStatement.setString(2, book.getTitle());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToGetBook("Unable to get book");
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        }

    }

    @Override
    public void unPublishBook(Book book) throws UnableToGetBook, UnableToConnect {
        try(var connection = getConnection()){
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Book SET isComplete = 0 WHERE isbn_Book = ? AND title_Book = ?");
            prepareStatement.setString(1, book.getIsbn());
            prepareStatement.setString(2, book.getTitle());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToGetBook("Unable to get book");
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        }
    }

    @Override
    public void deleteBook(Book currentBook) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            //Je l'id du livre
            int idBook = getBookId(currentBook, connection);
            //Je supprime tous les liens de la table Link_Pages qui contiennent les ids des pages du livre
            deleteLink(connection, idBook);
            //Je supprime toutes les pages du livre
            deletePage(connection, idBook);
            //Je supprime le livre
            deleteBook(connection, idBook);
            connection.commit();
        } catch (SQLException | UnableToConnect e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteLink(Connection connection, int idBook) throws SQLException {
        try (PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM Link_Pages WHERE id_CurrentPage IN (SELECT id_Page FROM Page WHERE id_Book = ?)")) {
            prepareStatement.setInt(1, idBook);
            prepareStatement.execute();
        }
    }

    private static void deletePage(Connection connection, int idBook) throws SQLException {
        try (PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM Page WHERE id_Book = ?")) {
            prepareStatement.setInt(1, idBook);
            prepareStatement.execute();
        }
    }

    private static void deleteBook(Connection connection, int idBook) throws SQLException {
        try (PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM Book WHERE id_Book = ?")) {
            prepareStatement.setInt(1, idBook);
            prepareStatement.execute();
        }
    }

    @Override
    public void saveBook(Book book) throws UnableToConnect, UnableToSaveBook {
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO Book (title_Book, resume_Book, isbn_Book, matricule_Autor) VALUES (?, ?, ?, ?)")) {
            prepareStatement.setString(1, book.getTitle());
            prepareStatement.setString(2, book.getResume());
            prepareStatement.setString(3, book.getIsbn());
            prepareStatement.setString(4, book.getAutorMatricule());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToSaveBook();
        }
    }

    @Override
    public List<Book> getAllBooksFromAutor(String id_Autor) throws UnableToGetAllBooks, UnableToConnect {
        var result = new ArrayList<Book>();
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("SELECT * FROM Book WHERE matricule_Autor = ?")) {
            prepareStatement.setString(1, id_Autor);
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            while (resSet.next()) {
                result.add(new Book(resSet.getString("title_Book"), resSet.getString("resume_Book"), resSet.getString("isbn_Book"), resSet.getString("matricule_Autor")));
            }
        } catch (SQLException e) {
            throw new UnableToGetAllBooks();
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        }
        return result;
    }

    @Override
    public void editBook(Book oldBook, Book newBook) throws UnableToEditBook, UnableToConnect {
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("UPDATE Book SET title_Book = ?, resume_Book = ? WHERE isbn_Book = ? AND title_Book = ?")) {
            prepareStatement.setString(1, newBook.getTitle());
            prepareStatement.setString(2, newBook.getResume());
            prepareStatement.setString(3, oldBook.getIsbn());
            prepareStatement.setString(4, oldBook.getTitle());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToEditBook();
        }
    }

    private int getBookId(Book book, Connection connection) throws UnableToConnect {
        try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT id_Book FROM Book WHERE isbn_Book = ? AND title_Book = ?")) {
            prepareStatement.setString(1, book.getIsbn());
            prepareStatement.setString(2, book.getTitle());
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            if (resSet.next()) {
                return resSet.getInt("id_Book");
            }
        } catch (SQLException e) {
            throw new UnableToConnect();
        }
        return -1;
    }

    @Override
    public void updatePageBook(Book currentBook) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            var idBook = getBookId(currentBook, connection);
            deletePage(connection, idBook);
            try (PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO Page (text_Page, number_Page, id_Book) VALUES (?, ?, ?)")) {
                for (Page page : currentBook.getPages()) {
                    prepareStatement.setString(1, page.getTextPage());
                    prepareStatement.setInt(2, page.getNumberPage());
                    prepareStatement.setInt(3, idBook);
                    prepareStatement.execute();
                }
            }
            connection.commit();
        } catch (SQLException | UnableToConnect e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des auteurs">
    @Override
    public int saveAutor(Autor autor) throws UnableToSaveAutor, UnableToConnect {
        int matricule = 0;
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO Autor (name_Autor, firstname_Autor) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, autor.getName());
            prepareStatement.setString(2, autor.getFirstname());
            prepareStatement.execute();

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    matricule = generatedKeys.getInt(1);
                } else {
                    throw new UnableToSaveAutor();
                }
            }

        } catch (SQLException e) {
            throw new UnableToSaveAutor();
        }

        return matricule;
    }

    @Override
    public List<Autor> getAllAutor() throws UnableToGetAllAutors, UnableToConnect {
        List<Autor> result = new ArrayList<>();
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("SELECT * FROM Autor")) {
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            while (resSet.next()) {
                result.add(new Autor(resSet.getString("name_Autor"), resSet.getString("firstname_Autor"), resSet.getString("matricule_Autor")));
            }
        } catch (SQLException e) {
            throw new UnableToGetAllAutors();
        }
        return result;
    }

    @Override
    public Autor getAutorByNames(String firstname, String lastname) throws UnableToGetAutor, UnableToConnect {
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("SELECT * FROM Autor WHERE name_Autor = ? AND firstname_Autor = ?")) {
            prepareStatement.setString(1, lastname);
            prepareStatement.setString(2, firstname);
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            if (resSet.next()) {
                return new Autor(resSet.getString("name_Autor"), resSet.getString("firstname_Autor"), resSet.getString("matricule_Autor"));
            }
        } catch (SQLException e) {
            throw new UnableToGetAutor();
        }
        return null;
    }


    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des pages">

    @Override
    public List<Page> getPagesExceptArg(Book book, int pageNumber) throws UnableToConnect, UnableToGetPages, UnableToConstructPage, UnableToGetPageId {
        try (Connection connection = getConnection()) {
            int idPage;
            PreparedStatement prepareStatement;
            if(pageNumber == 0){
                String query = "SELECT *\n" +
                        "FROM Page\n" +
                        "WHERE id_Book = ?\n" +
                        "  AND number_Page <> ?\n";
                 prepareStatement = connection.prepareStatement(query);
                prepareStatement.setInt(1, getBookId(book, connection));
                prepareStatement.setInt(2, pageNumber);
            }else{
                idPage = getPageId(pageNumber, book.getPages().get(pageNumber - 1).getTextPage());
                String query = "SELECT *\n" +
                        "FROM Page\n" +
                        "WHERE id_Book = ?\n" +
                        "  AND number_Page <> ?\n" +
                        "  AND id_Page NOT IN (SELECT id_NextPage FROM Link_Pages WHERE id_CurrentPage = ?);\n";
                 prepareStatement = connection.prepareStatement(query);
                prepareStatement.setInt(1, getBookId(book, connection));
                prepareStatement.setInt(2, pageNumber);
                prepareStatement.setInt(3, idPage);
            }



            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            List<Page> result = new ArrayList<>();
            while (resSet.next()) {
                result.add(new Page(resSet.getString("text_Page"), resSet.getInt("number_Page")));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToGetPages();
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        } catch (UnableToConstructPage e) {
            throw new UnableToConstructPage();
        } catch (UnableToGetPageId e) {
            throw new UnableToGetPageId();
        }


    }

    @Override
    public Book addPagesToBook(Book book) throws UnableToGetAllPages, UnableToConstructPage, UnableToAddPage {
        try (var connection = getConnection()) {
            int idBook = getBookId(book, connection);
            if (idBook == -1) return book;
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM Page WHERE id_Book = ?");
            prepareStatement.setInt(1, idBook);
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            var pagesNumber = book.getPagesNumber();
            while (resSet.next()) {
                var page = new Page(resSet.getString("text_Page"), resSet.getInt("number_Page"));
                if (!pagesNumber.contains(page.getNumberPage())) {
                    book.addPage(page);
                }
            }
        } catch (UnableToConnect e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new UnableToGetAllPages();
        }
        return book;
    }

    @Override
    public void updatePage(Page page, String text) throws UnableToUpdatePage, UnableToConnect {
        try (var connection = getConnection()){
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE Page SET text_Page = ? WHERE number_Page = ? AND text_Page = ?");
            prepareStatement.setString(1, text);
            prepareStatement.setInt(2, page.getNumberPage());
            prepareStatement.setString(3, page.getTextPage());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToUpdatePage();
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        }
    }

    @Override
    public void createPage(String text, Book book) throws UnableToUpdatePage, UnableToConnect, UnableToCreatePage {
        try (var connection = getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO Page (text_Page, number_Page, id_Book) VALUES (?, ?, ?)");
            prepareStatement.setString(1, text);
            //prepareStatement.setInt(2, ;
            prepareStatement.setInt(3, getBookId(book, connection));

        } catch (SQLException e) {
            throw new UnableToConnect();
        }
    }

    private int getPageId(int pageNumber, String text) throws UnableToConnect, UnableToGetPageId {
        try (Connection connection = getConnection()) {
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT id_Page FROM Page WHERE number_Page = ? AND text_Page = ?");
            prepareStatement.setInt(1, pageNumber);
            prepareStatement.setString(2, text);
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            if (resSet.next()) {
                return resSet.getInt("id_Page");
            }
        } catch (SQLException e) {
            throw new UnableToGetPageId();
        } catch (UnableToConnect e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des liens">
    @Override
    public void addNewLink(Page currentPage, Page nextPage, String text, Book book) throws UnableToGetPageId, UnableToConnect, UnableToAddLink {
        try (var connection = getConnection()) {
            var currentPageId = getPageId(currentPage.getNumberPage(), currentPage.getTextPage());
            var nextPageId = getPageId(nextPage.getNumberPage(), nextPage.getTextPage());
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO Link_Pages (id_CurrentPage,id_NextPage,Content_Link,id_Book) VALUES (?, ?, ?,?)");
            prepareStatement.setInt(1, currentPageId);
            prepareStatement.setInt(2, nextPageId);
            prepareStatement.setString(3, text);
            prepareStatement.setInt(4, getBookId(book, connection));
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToAddLink();
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        } catch (UnableToGetPageId e) {
            throw new UnableToGetPageId();
        }
    }

    @Override
    public List<Link> getAllLinkOfPage(Book book, Page page) throws UnableToGetAllLinks, UnableToConstructLink, UnableToGetPageId, UnableToConnect {
        try (Connection connection = getConnection()) {
            var idPage = getPageId(page.getNumberPage(), page.getTextPage());
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT *\n" +
                    "FROM Link_Pages\n" +
                    "WHERE id_CurrentPage = ?;");
            prepareStatement.setInt(1, idPage);
            prepareStatement.execute();
            var resSet = prepareStatement.executeQuery();
            List<Link> result = new ArrayList<>();
            while (resSet.next()) {
                result.add(new Link(resSet.getInt("id_CurrentPage"), resSet.getInt("id_NextPage"), resSet.getString("Content_Link")));
            }
            return result;
        } catch (SQLException e) {
            throw new UnableToGetAllLinks(e.getMessage());
        } catch (UnableToConnect e) {
            throw new UnableToConnect();
        } catch (UnableToGetPageId e) {
            throw new UnableToGetPageId();
        }
    }

    public void updateAllLink(List<Link> listLink, Book book) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            var idBook = getBookId(book, connection);

            //Supprime tous les liens de la table Link_Pages qui sont dans la liste en paramètre
            try {
                PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM Link_Pages WHERE id_Book = ?");
                prepareStatement.setInt(1, idBook);
                prepareStatement.execute();
            } catch (SQLException e) {
                throw new UnableToConnect();
            }
            //Ajoute tous les liens de la liste en paramètre
            try {
                PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO Link_Pages (id_CurrentPage,id_NextPage,Content_Link,id_Book) VALUES (?, ?, ?,?)");
                for (Link link : listLink) {
                    prepareStatement.setInt(1, link.getIdCurrentPage() );
                    prepareStatement.setInt(2, link.getIdNextPage());
                    prepareStatement.setString(3, link.getLinkContent()) ;
                    prepareStatement.setInt(4, idBook);
                    prepareStatement.execute();
                }
            } catch (SQLException e) {
                throw new UnableToConnect();
            }
            connection.commit();
        } catch (SQLException | UnableToConnect e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement interne">

    /**
     * Permet de recuperer une connexion a la base de donnees
     * Idée : en faire une factory
     *
     * @return la COnnection à la BD
     * @throws SQLException
     */
    private Connection getConnection() throws UnableToConnect {
        try {
            return DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            throw new UnableToConnect();
        }
    }
    //</editor-fold>
}
