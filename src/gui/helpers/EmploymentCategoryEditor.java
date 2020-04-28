package gui.helpers;

import model.enums.EmploymentCategory;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

/**
 * Class is responsible to implement editing tree leaf
 */
public class EmploymentCategoryEditor extends AbstractCellEditor implements TableCellEditor {

    /**
     * Field for JComboBox component
     */
    private final JComboBox<EmploymentCategory> combo;

    /**
     * Constructor
     * Initializes {@link EmploymentCategoryEditor#combo}
     */
    public EmploymentCategoryEditor() {
        combo = new JComboBox<>(EmploymentCategory.values());
    }

    /**
     * {@inheritDoc}
     *
     * @param table
     * @param value
     * @param isSelected
     * @param row
     * @param column
     * @return
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        combo.setSelectedItem(value);
        combo.addActionListener(e -> fireEditingStopped());
        return combo;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Object getCellEditorValue() {
        return combo.getSelectedItem();
    }

    /**
     * {@inheritDoc}
     *
     * @param e {@link EventObject} object
     * @return always true (because it used to set Employment category editable
     */
    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }
}
