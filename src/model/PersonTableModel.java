package model;

import model.enums.EmploymentCategory;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Table model to store data for Table view
 */
public class PersonTableModel extends AbstractTableModel {

    /**
     * Store data for each row in form of List
     */
    private List<PersonModel> rows;

    /**
     * Store columns' names
     */
    private final String[] colNames = {"ID", "Name", "Occupation", "Age Category",
            "Employment Category", "US Citizen", "Tax ID", "Gender"};

    /**
     * Returns column name
     *
     * @param columnId column's id in colNames array
     * @return column name
     */
    @Override
    public String getColumnName(int columnId) {
        return colNames[columnId];
    }

    /**
     * Set value to a specific cel coulmn
     *
     * @param aValue value to be set
     * @param row    row index
     * @param col    column index
     */
    @Override
    public void setValueAt(Object aValue, int row, int col) {

        if (rows == null) return;

        PersonModel person = rows.get(row);

        switch (col) {
            case 1:
                person.setName((String) aValue);
                break;
            case 4:
                person.setEmpCat((EmploymentCategory) aValue);
                break;
            case 5:
                person.setUsCitizen((Boolean) aValue);
                break;
            default:
                break;
        }
    }

    /**
     * Get info if cell is editable
     *
     * @param row row index
     * @param col column index
     * @return if provided cell is editable
     */
    @Override
    public boolean isCellEditable(int row, int col) {

        switch (col) {
            case 1:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }

    /**
     * Sets list of {@link PersonModel} objects for the table's data
     *
     * @param rows list of {@link PersonModel} objects
     */
    public void setData(List<PersonModel> rows) {
        this.rows = rows;
    }

    /**
     * Returns number of columns
     *
     * @return length of colName
     */
    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    /**
     * Returns number of rows
     *
     * @return rows quantity
     */
    @Override
    public int getRowCount() {
        return rows.size();
    }

    /**
     * Returns value at cell of given coordinates
     *
     * @param row row index
     * @param col column index
     * @return value at specified Cell or null if cell not found
     */
    @Override
    public Object getValueAt(int row, int col) {
        PersonModel person = rows.get(row);

        switch (col) {
            case 0:
                return person.getId();
            case 1:
                return person.getName();
            case 2:
                return person.getOccupation();
            case 3:
                return person.getAgeCategory();
            case 4:
                return person.getEmpCat();
            case 5:
                return person.isUsCitizen();
            case 6:
                return person.getTaxId();
            case 7:
                return person.getGender();
        }

        return null;
    }

    /**
     * Returns class of given column index
     *
     * @param columnIndex column index
     * @return class of given column index or null if column index not found
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
            case 2:
            case 3:
            case 6:
            case 7:
                return String.class;
            case 4:
                return EmploymentCategory.class;
            case 5:
                return Boolean.class;
            default:
                return null;
        }
    }
}
