package model;

import model.enums.AgeCategory;
import model.enums.EmploymentCategory;
import model.enums.Gender;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Class responsible to perform operations with database
 */
public class Database {

    /**
     * List of People
     */
    private final List<PersonModel> people;

    /**
     * Connection object
     */
    private Connection conn;

    /**
     * Database port
     */
    private int port;

    /**
     * Database user name
     */
    private String user;

    /**
     * Database user password
     */
    private String password;

    /**
     * Constructor. Initializes people list
     */
    public Database() {
        this.people = new LinkedList<>();
    }

    /**
     * Method verifies if driver class exists
     *
     * @throws SQLException if driver doesn't exists
     */
    private void driverMySQLClassExists() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver not found");
        }
    }

    /**
     * Method used for set initial values for Database connection
     *
     * @param port     port value
     * @param user     user name value
     * @param password passwor value
     * @throws SQLException if driver doesn't exists
     */
    public void configure(int port, String user, String password) throws SQLException {

        this.port = port;
        this.user = user;
        this.password = password;

        if (conn != null) return;

        driverMySQLClassExists();
    }

    /**
     * Connect to DataBase if not connected earlier
     *
     * @throws Exception if driver not found
     */
    public void connect() throws Exception {

        if (conn != null) return;

        driverMySQLClassExists();

        // connecting to db
        String url = "jdbc:mysql://localhost:" + this.port + "/swingtest?serverTimezone=UTC";
        conn = DriverManager.getConnection(url, this.user, this.password);
    }

    /**
     * Method disconnects from database
     */
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection");
            }
        }
    }

    /**
     * Method saves all the people to database (one by one)
     *
     * @throws SQLException if exception occurs while preparing the statement
     */
    public void save() throws SQLException {

        String checkSQL = "SELECT COUNT(*) AS count FROM people WHERE id=?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSQL);

        String insertSql = "INSERT INTO people " +
                "(id, name, age, employment_status, tax_id, us_citizen, gender, occupation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertSql);

        String updateSql = "UPDATE people SET " +
                "name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? " +
                "WHERE id=?";
        PreparedStatement updateStmt = conn.prepareStatement(updateSql);

        for (PersonModel person : people) {
            int id = person.getId();
            String name = person.getName();
            String occupation = person.getOccupation();
            AgeCategory age = person.getAgeCategory();
            EmploymentCategory emp = person.getEmpCat();
            String tax = person.getTaxId();
            boolean isUs = person.isUsCitizen();
            Gender gender = person.getGender();

            checkStmt.setInt(1, id);
            ResultSet checkResult = checkStmt.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);

            int col = 1;

            if (count == 0) {
                System.out.println("Inserting person with ID " + id);

                insertStmt.setInt(col++, id);
                insertStmt.setString(col++, name);
                insertStmt.setString(col++, age.name());
                insertStmt.setString(col++, emp.name());
                insertStmt.setString(col++, tax);
                insertStmt.setBoolean(col++, isUs);
                insertStmt.setString(col++, gender.name());
                insertStmt.setString(col, occupation);

                insertStmt.executeUpdate();
            } else {
                System.out.println("Updating person with ID " + id);

                updateStmt.setString(col++, name);
                updateStmt.setString(col++, age.name());
                updateStmt.setString(col++, emp.name());
                updateStmt.setString(col++, tax);
                updateStmt.setBoolean(col++, isUs);
                updateStmt.setString(col++, gender.name());
                updateStmt.setString(col++, occupation);
                updateStmt.setInt(col, id);

                updateStmt.executeUpdate();
            }
        }

        insertStmt.close();
        checkStmt.close();
    }

    /**
     * Method loads all data from people table
     *
     * @throws SQLException if exception occurs while preparing the statement or querying
     */
    public void load() throws SQLException {
        people.clear();

        String sql = "SELECT id, name, age, employment_status, tax_id, us_citizen, gender, occupation " +
                "FROM people " +
                "ORDER BY name";
        Statement selectStatement = conn.createStatement();

        ResultSet results = selectStatement.executeQuery(sql);

        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String age = results.getString("age");
            String emp = results.getString("employment_status");
            String taxId = results.getString("tax_id");
            boolean isUs = results.getBoolean("us_citizen");
            String gender = results.getString("gender");
            String occ = results.getString("occupation");

            PersonModel person = new PersonModel(id, name, occ,
                    AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp),
                    taxId, isUs, Gender.valueOf(gender));
            people.add(person);
            System.out.println(person);
        }

        results.close();
        selectStatement.close();
    }

    /**
     * Adds person to the list
     *
     * @param person {@link PersonModel} object to add
     */
    public void addPerson(PersonModel person) {
        people.add(person);
    }

    /**
     * Removes person from the people list
     *
     * @param row index to remove
     */
    public void removePerson(int row) {
        people.remove(row);
    }

    /**
     * Returns list of persons from the table
     *
     * @return List of {@link PersonModel}s objects
     */
    public List<PersonModel> getPeople() {
        // prevent other classes from modifying list they retrieve
        return Collections.unmodifiableList(people);
    }

    /**
     * Writes array of {@link PersonModel} to given file
     *
     * @param file {@link File} given file, where info is to be stored
     * @throws IOException if failed to write the file
     */
    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        PersonModel[] persons = people.toArray(new PersonModel[people.size()]);

        oos.writeObject(persons);
        oos.close();
    }

    /**
     * Gets all persons from the given file. Writes it to the table
     *
     * @param file {@link File} given file, where info is stored
     * @throws IOException if failed to read the file
     */
    public void loadFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        try {
            PersonModel[] person = (PersonModel[]) ois.readObject();
            people.clear();
            people.addAll(Arrays.asList(person));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ois.close();
    }
}
