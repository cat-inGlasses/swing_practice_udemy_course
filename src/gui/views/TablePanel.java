package gui.views;

import gui.helpers.EmploymentCategoryEditor;
import gui.helpers.EmploymentCategoryRenderer;
import gui.listeners.PersonTableListener;
import model.enums.EmploymentCategory;
import model.PersonModel;
import model.PersonTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Class is responsible for interacting with table in tablePane
 */
public class TablePanel extends JPanel {

    /**
     * For table
     */
    private final JTable table;

    /**
     * For table Model
     */
    private final PersonTableModel tableModel;

    /**
     * For context menu
     */
    private final JPopupMenu popupMenu;

    /**
     * For listener object
     */
    private PersonTableListener personTableListener;

    /**
     * Table pane constructor
     */
    public TablePanel() {

        // initialization
        tableModel = new PersonTableModel();
        table = new JTable(tableModel);
        popupMenu = new JPopupMenu();

        // set renderer and editor for Employment column
        table.setDefaultRenderer(EmploymentCategory.class, new EmploymentCategoryRenderer());
        table.setDefaultEditor(EmploymentCategory.class, new EmploymentCategoryEditor());

        table.setRowHeight(25);

        JMenuItem removeItem = new JMenuItem("Delete row");
        popupMenu.add(removeItem);

        setListeners(removeItem);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    /**
     * Method sets Listeners for Table panel
     *
     * @param removeItem menu item
     */
    private void setListeners(JMenuItem removeItem) {
        // showing context menu
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });

        // action on delete click
        removeItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (personTableListener != null) {
                personTableListener.rowDeleted(row);
                tableModel.fireTableRowsDeleted(row, row);
            }
        });
    }

    /**
     * set Data to tableModel
     *
     * @param data list of {@link PersonModel}s
     */
    public void setData(List<PersonModel> data) {
        tableModel.setData(data);
    }

    /**
     * Mehod fires notification for event listeners
     */
    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    /**
     * Method sets object for preforming actions on event occurring
     *
     * @param personTableListener {@link PersonTableListener} object
     */
    public void addPersonTableListener(PersonTableListener personTableListener) {
        this.personTableListener = personTableListener;
    }
}