package controller;

import gui.helpers.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// controller used to create connection between View and model
// we must not use model in view, only through controller

/**
 * controller used to create connection between View and model
 */
public class Controller {

    /**
     * Database instance
     */
    Database db = new Database();

    /**
     * Query the {@link Database#getPeople} to retrieve list of users
     *
     * @return List of {@link PersonModel} objects
     */
    public List<PersonModel> getPeople() {
        return db.getPeople();
    }

    /**
     * Prepare data and adds a person to Database. Uses {@link Database#addPerson} to add user
     *
     * @param ev object of {@link FormEvent} which contains all data needed to be
     *           passed after button "OK" clicked
     */
    public void addPerson(FormEvent ev) {

        db.addPerson(PersonModel
                .getBuilder()
                .withName(ev.getName())
                .withOccupation(ev.getOccupation())
                .withCategory(ev.getAgeCategory())
                .withEmployment(ev.getEmploymentCategory())
                .withUSCitizen(ev.isUsCitizen(), ev.getTaxId())
                .withGender(ev.getGender())
                .build()
        );
    }

    /**
     * Ask {@link Database} to remove person.
     *
     * @param row number of row to delete (start from 0)
     */
    public void removePerson(int row) {
        db.removePerson(row);
    }

    /**
     * Sends choosed file to be saved to {@link Database#saveToFile(File)} function
     *
     * @param file {@link File} object
     * @throws IOException if failed to write the file
     */
    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }

    /**
     * Sends choosed file from wich info choul be loaded to {@link Database#loadFromFile(File)} function
     *
     * @param file {@link File} object
     * @throws IOException if failed to read the file
     */
    public void loadFromFile(File file) throws IOException {
        db.loadFromFile(file);
    }

    /**
     * Method configures database connection credentials
     *
     * @param port     port
     * @param user     user name
     * @param password user password
     * @throws SQLException if driver doesn't exists
     */
    public void configure(int port, String user, String password) throws SQLException {
        db.configure(port, user, password);
    }

    /**
     * Method wraps {@link Database#connect()} method to database
     *
     * @throws Exception if driver doesn't exists
     */
    public void connect() throws Exception {
        db.connect();
    }

    /**
     * Method wraps database {@link Database#load()} method
     *
     * @throws SQLException if exception occurs while preparing the statement or querying
     */
    public void load() throws SQLException {
        db.load();
    }

    /**
     * Method wraps {@link Database#save()} method
     *
     * @throws SQLException if exception occurs while preparing the statement or querying
     */
    public void save() throws SQLException {
        db.save();
    }

    /**
     * Method wraps {@link Database#disconnect()} method
     */
    public void close() {
        db.disconnect();
    }
}
