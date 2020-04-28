package gui.dialogs;

import gui.listeners.PrefsListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Class is responsible for interacting with preferences form
 */
public class PrefsDialog extends JDialog {

    /**
     * Button is responsible for accepting changes
     */
    private final JButton okBtn;

    /**
     * Button is responsible for cancel editing preferences
     */
    private final JButton cancelBtn;

    /**
     * Input text field with spinner function
     * allows only number values
     */
    private final JSpinner portSpinner;

    /**
     * Text field for database user name
     */
    private final JTextField userField;

    /**
     * Password field for database user password
     */
    private final JPasswordField passField;

    /**
     * listener object
     * responsible for preform actions when some listening evens occur
     */
    private PrefsListener prefsListener;

    /**
     * Progress dialog constructor
     *
     * @param parent modal parent
     */
    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);

        // initializations
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        // spinner use model to set its properties
        portSpinner = new JSpinner(new SpinnerNumberModel(3306, 0, 9999, 1));
        userField = new JTextField(10);
        passField = new JPasswordField(10);
        passField.setEchoChar('*');

        setLayoutControls();

        setListeners();

        // other view properties
        setSize(340, 250);
        setLocationRelativeTo(parent);
    }

    /**
     * Setting form listners
     */
    private void setListeners() {
        // if approved - gets values from form and sen it to listener object
        okBtn.addActionListener(e -> {
            Integer port = (Integer) portSpinner.getValue();
            String user = userField.getText();
            char[] password = passField.getPassword();
            setVisible(false);

            if (prefsListener != null) {
                prefsListener.setPreferences(user, new String(password), port);
            }
        });

        // hide modal window if canceled
        cancelBtn.addActionListener(e -> setVisible(false));
    }

    /**
     * Sets view of modal window
     */
    private void setLayoutControls() {

        // divide modal window into two parts: with fields and with controls (buttons)
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        // creating doubled border for visible border (inner) and margin boarder (outer)
        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Database preferences");
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

        // Setting layout
        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        // setting objects for paddings
        gc.gridy = 0;
        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets noPadding = new Insets(0, 0, 0, 0);

        //-----------------------------------------
        // First row

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(userField, gc);

        //-----------------------------------------
        // next row

        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        gc.fill = GridBagConstraints.HORIZONTAL;
        controlsPanel.add(passField, gc);

        //-----------------------------------------
        // next row

        gc.gridy++;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Port: "), gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(portSpinner, gc);

        //-----------------------------------------
        // buttons panel

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okBtn);
        buttonsPanel.add(cancelBtn);

        Dimension btnSize = cancelBtn.getPreferredSize();
        okBtn.setPreferredSize(btnSize);

        //-----------------------------------------
        // add panels to dialog:

        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Set sefault values for input elements of preferences modal window
     *
     * @param user     - user name
     * @param password - password
     * @param port     - port
     */
    public void setDefaults(String user, String password, int port) {
        userField.setText(user);
        passField.setText(password);
        portSpinner.setValue(port);
    }

    /**
     * Sets listener. Use to untie view from controller
     *
     * @param prefsListener - object of Class that implements listener for this prefs form
     */
    public void setPrefsListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }
}
