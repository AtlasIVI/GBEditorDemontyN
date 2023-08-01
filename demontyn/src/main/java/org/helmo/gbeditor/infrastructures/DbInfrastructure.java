package org.helmo.gbeditor.infrastructures;

import org.helmo.gbeditor.models.Autor;
import org.helmo.gbeditor.repository.RepositoryInterface;
import org.helmo.gbeditor.repository.exceptions.UnableToConnect;
import org.helmo.gbeditor.repository.exceptions.UnableToGetAllAutors;
import org.helmo.gbeditor.repository.exceptions.UnableToSaveAutor;
import org.helmo.gbeditor.repository.exceptions.UnableToSaveBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbInfrastructure implements RepositoryInterface {

    private String dbUrl;
    private String username;
    private String password;
    private String driverName;


    public DbInfrastructure(String driverName, String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
        this.driverName = driverName;
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des livres">
    @Override
    public void saveBook(String bookTitle, String bookResume, String isbn, Autor autor) throws UnableToConnect, UnableToSaveBook {
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO Book (title_Book, resume_Book, isbn_Book, matricule_Autor) VALUES (?, ?, ?, ?)")) {
            prepareStatement.setString(1, bookTitle);
            prepareStatement.setString(2, bookResume);
            prepareStatement.setString(3, isbn);
            prepareStatement.setString(4, autor.getMatricule());
            prepareStatement.execute();
        } catch (SQLException e) {
            throw new UnableToSaveBook();
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Les methodes de traitement BD des auteurs">
    @Override
    public int saveAutor(Autor autor) throws UnableToSaveAutor, UnableToConnect {
        int matricule = 0;
        try (PreparedStatement prepareStatement = getConnection().prepareStatement("INSERT INTO Autor (name_Autor, firstname_Autor) VALUES (?, ?)",Statement.RETURN_GENERATED_KEYS)) {
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
