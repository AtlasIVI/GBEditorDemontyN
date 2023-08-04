package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.models.Book;
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
