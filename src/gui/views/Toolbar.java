package gui.views;

import gui.helpers.Utils;
import gui.listeners.ToolbarListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class is responsible for interacting with toolbar panel
 */
public class Toolbar extends JToolBar implements ActionListener {

    /**
     * Field for saveBtn component
     */
    private final JButton saveBtn;

    /**
     * field for refreshBtn component
     */
    private final JButton refreshBtn;

    /**
     * Field for listener object
     */
    private ToolbarListener toolbarListener;

    /**
     * Toolbar Constructor
     */
    public Toolbar() {
        // get rid of the boarder if you want the toolbar draggable
        setBorder(BorderFactory.createEtchedBorder());

        // disable floating
        // setFloatable(false);

        saveBtn = new JButton();
        saveBtn.setIcon(Utils.createIcon("/images/Save16.png"));
        saveBtn.setToolTipText("Save");

        refreshBtn = new JButton();
        refreshBtn.setIcon(Utils.createIcon("/images/Refresh16.png"));
        refreshBtn.setToolTipText("Refresh");

        // setting listeners
        saveBtn.addActionListener(this);
        refreshBtn.addActionListener(this);

        add(saveBtn);
        // addSeparator();
        add(refreshBtn);
    }

    /**
     * Method sets listener for Tool
     *
     * @param listener listener object
     */
    public void setListener(ToolbarListener listener) {
        toolbarListener = listener;
    }

    /**
     * {@inheritDoc}
     *
     * @param e {@link ActionEvent} object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == saveBtn) {
            if (toolbarListener != null) {
                toolbarListener.saveEventOccurred();
            }
        } else if (clicked == refreshBtn) {
            if (toolbarListener != null) {
                toolbarListener.refreshEventOccurred();
            }
        }
    }
}
