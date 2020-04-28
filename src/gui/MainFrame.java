package gui;

import controller.Controller;
import gui.dialogs.PrefsDialog;
import gui.helpers.PersonFileFilter;
import gui.listeners.ToolbarListener;
import gui.views.FormPanel;
import gui.views.MessagePanel;
import gui.views.TablePanel;
import gui.views.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

/**
 * Class is responsible for creating main application window
 */
public class MainFrame extends JFrame {

    /**
     * Top toolbar widget
     */
    private final Toolbar toolbar;

    /**
     * Left form Panel widget
     */
    private final FormPanel formPanel;

    /**
     * Split panel do divide Form panel and Tabbed Panel
     */
    private final JSplitPane splitPane;

    /**
     * Right Panel with tabs
     */
    private final JTabbedPane tabPane;

    /**
     * Panel with table (has to be in the first tab)
     */
    private final TablePanel tablePanel;

    /**
     * Panel with server messages (has to be in the second tab)
     */
    private final MessagePanel messagePanel;

    /**
     * File chooser dialog instance
     */
    private final JFileChooser fileChooser;

    /**
     * Preferences modal window
     */
    private PrefsDialog prefsDialog;

    /**
     * Preferences instances
     */
    private Preferences prefs;

    /**
     * Controller instance. Use to connect with model instances and preform business logic
     */
    private final Controller controller;

    /**
     * MainFrame constructor preforms main initializations and configurations
     *
     * @throws HeadlessException read HeadlessException description
     */
    public MainFrame() throws HeadlessException {
        super("Hello World");

        setLookAndFeels();

        // initialization
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        tablePanel = new TablePanel();
        controller = new Controller();
        fileChooser = new JFileChooser();
        tabPane = new JTabbedPane();
        messagePanel = new MessagePanel(this);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabPane);

        setJMenuBar(createMenuBar());

        setPreferences();

        setListeners();

        // add possibility to expand/collapse panels beside splitPane
        splitPane.setOneTouchExpandable(true);

        // setting tabs for Tabbed pane
        tabPane.addTab("Person Database", tablePanel);
        tabPane.addTab("Messages", messagePanel);

        // set filter for file choosing (from menu)
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        // initialization data for table in TablePane
        tablePanel.setData(controller.getPeople());

        // set layout and adding components into window
        setLayout(new BorderLayout());
        add(toolbar, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);

        // refresh table after window loads
        refreshTable();

        // setting the window
        setMinimumSize(new Dimension(500, 400));
        setSize(800, 500);
        // do nothing, because we want to close connection before quiting (in listener)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Method sets Listeners:
     * <ul>
     *     <li>toolbar buttons</li>
     *     <li>adding person from From panel</li>
     *     <li>change tab in Tabbed panel</li>
     *     <li>window closing listener</li>
     * </ul>
     */
    private void setListeners() {

        // set what to do on toolbar's btn clicking
        toolbar.setListener(new ToolbarListener() {
            @Override
            public void saveEventOccurred() {
                connect();
                try {
                    controller.save();
                } catch (SQLException e) {
                    showErrorMessageDialog("Unable to save to database.", "Database connection problem");
                }
            }

            @Override
            public void refreshEventOccurred() {
                refreshTable();
            }
        });

        // adding person from Form panel
        formPanel.setFormListener(e -> {
            controller.addPerson(e);
            tablePanel.refresh();
        });

        // remove person from table (with context menu)
        tablePanel.addPersonTableListener(controller::removePerson);

        // listener on changes the tab in tabbed panel
        tabPane.addChangeListener(e -> {
            int tabIndex = tabPane.getSelectedIndex();
            if (tabIndex == 1) messagePanel.refresh();
        });

        // dispose all windows and remove garbage (also closing connection to db)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Window closing");
                dispose();
                System.gc();
            }
        });
    }

    /**
     * Method sets look and feels of program
     * See more on <a href="https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/index.html">oracle.com</a>
     */
    private void setLookAndFeels() {

        // https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/index.html
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Can't set look and feel.");
        }

        /*
        try {
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            System.out.println("Can't set look and feel.");
        }
        */
    }

    /**
     * Method refreshes table in TablePane with data loaded fom Database
     */
    public void refreshTable() {
        if (!connect()) return;

        try {
            controller.load();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Unable to load from database.",
                    "Database connection problem",
                    JOptionPane.ERROR_MESSAGE);
        }

        tablePanel.refresh();
    }

    /**
     * Method wraps {@link Controller#connect} method (for Database connection)
     *
     * @return true if connect successfully
     */
    public boolean connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Cannot connect to database.",
                    "Database connection problem",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Method sets preferences interaction
     */
    private void setPreferences() {
        // where to store application preferences
        prefs = Preferences.userRoot().node("db");

        // set listener for preferences dialog
        prefsDialog = new PrefsDialog(this);
        prefsDialog.setPrefsListener((user, password, port) -> {
            prefs.put("user", user);
            prefs.put("password", password);
            prefs.putInt("port", port);

            try {
                controller.configure(port, user, password);
            } catch (SQLException e) {
                showErrorMessageDialog("Connection error", "Unable to connect");
            }
        });

        // setting default preferences setting
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);

        prefsDialog.setDefaults(user, password, port);

        // set configuration
        try {
            controller.configure(port, user, password);
        } catch (SQLException e) {
            showErrorMessageDialog("Can't connect to Database", "Driver not found");
        }
    }

    /**
     * Method shows error modal dialog with error message
     *
     * @param title dialog title
     * @param msg   dialog message
     */
    public void showErrorMessageDialog(String title, String msg) {
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Creating menu for application
     *
     * @return {@link JMenuBar} object
     */
    public JMenuBar createMenuBar() {

        //// ------------------------------------------------------------------------
        //// ------------------------------------------------------------------------
        // File menu
        JMenu fileMenu = new JMenu("File");
        // menu name with '...' by convention means that will be shown popup window
        JMenuItem exportDataItem = new JMenuItem("Export data...");
        JMenuItem importDataItem = new JMenuItem("Import data...");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // - mnemonics
        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        // - accelerators
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        // - listeners
        importDataItem.addActionListener(e -> {
            if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.loadFromFile(fileChooser.getSelectedFile());
                    tablePanel.refresh();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Could not load data from file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        exportDataItem.addActionListener(e -> {
            if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.saveToFile(fileChooser.getSelectedFile());
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Could not save data to file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //// ------------------------------------------------------------------------
        //// ------------------------------------------------------------------------
        // Window menu
        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JMenuItem prefsItem = new JMenuItem("Preferences...");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person form");
        showFormItem.setSelected(true);
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        // - accelerators
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        // exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        // - listeners
        prefsItem.addActionListener(e -> prefsDialog.setVisible(true));
        showFormItem.addActionListener(e -> {
            JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
            if (menuItem.isSelected()) {
                splitPane.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
            }
            formPanel.setVisible(menuItem.isSelected());
        });
        exitItem.addActionListener(e -> {
            int action = JOptionPane.showConfirmDialog(MainFrame.this,
                    "Do you really want to exit application?",
                    "Confirm Exit",
                    JOptionPane.OK_CANCEL_OPTION);
            if (action == JOptionPane.OK_OPTION) {
                // triggering window closing event for all window listeners
                WindowListener[] listeners = getWindowListeners();
                for (WindowListener listener : listeners) {
                    listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                }
            }
        });

        //// ------------------------------------------------------------------------
        //// ------------------------------------------------------------------------
        // appending Menu Items to Main menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        // to put menu on Mac menu bar
        // System.setProperty("apple.laf.useScreenMenuBar", "true");

        return menuBar;
    }
}
