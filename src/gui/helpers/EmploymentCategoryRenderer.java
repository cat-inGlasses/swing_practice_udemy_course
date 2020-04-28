package gui.helpers;

import model.enums.EmploymentCategory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Class is responsible for render for each employment category
 */
public class EmploymentCategoryRenderer implements TableCellRenderer {

    /**
     * Field containd JComboBox component
     */
    private final JComboBox combo;

    /**
     * Constructor
     * provides initialization of combobox with employment values
     */
    public EmploymentCategoryRenderer() {
        combo = new JComboBox(EmploymentCategory.values());
    }

    /**
     * {@inheritDoc}
     *
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param column
     * @return
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        combo.setSelectedItem(value);
        return combo;
    }
}
