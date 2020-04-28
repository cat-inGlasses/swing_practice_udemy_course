package gui.dialogs;

import gui.listeners.ProgressDialogListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class responsible for interacting with progress dialog modal window
 */
public class ProgressDialog extends JDialog {

    /**
     * Button responsible for interruptin loading process
     */
    private final JButton cancelBtn;

    /**
     * progress bar component.
     * responsible for showing data loading process
     */
    private final JProgressBar progressBar;

    /**
     * listener object
     * responsible for preform actions when some listening evens occur
     */
    private ProgressDialogListener listener;

    /**
     * Progress dialog constructor
     *
     * @param parent - modal parent
     * @param title  - modal title
     */
    public ProgressDialog(Window parent, String title) {
        super(parent, title, ModalityType.APPLICATION_MODAL);

        // initialization
        cancelBtn = new JButton("Cancel");
        progressBar = new JProgressBar();

        // setting possibility of drawing string on progress bar
        progressBar.setStringPainted(true);
        progressBar.setString("Retrieving message...");
        progressBar.setMaximum(10);

        // if data amount is not determined
        // progressBar.setIndeterminate(true);

        // Listeners
        setListeners();

        // set size
        Dimension size = cancelBtn.getPreferredSize();
        size.width = 400;
        progressBar.setPreferredSize(size);

        setLayout(new FlowLayout());
        add(progressBar);
        add(cancelBtn);

        pack();

        // other settings
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(parent);
    }

    /**
     * Set Listeners for
     * - cancel button
     * - window closing event
     */
    private void setListeners() {
        cancelBtn.addActionListener(e -> {
            if (listener != null) {
                listener.cancelLoading();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (listener != null) {
                    listener.cancelLoading();
                }
            }
        });
    }

    /**
     * Sets listener. Use to untie view from controller
     *
     * @param dialogListener - object of Class that implements listener for this progress dialog form
     */
    public void setListener(ProgressDialogListener dialogListener) {
        this.listener = dialogListener;
    }

    /**
     * Set maximum value for progress bar
     *
     * @param maxValue maximum value for progress bar
     */
    public void setMaximum(int maxValue) {
        progressBar.setMaximum(maxValue);
    }

    /**
     * Sets progress line and text about progress for its line
     *
     * @param value current value of progress
     */
    public void setValue(int value) {
        int progress = 100 * value / progressBar.getMaximum();
        progressBar.setValue(value);
        progressBar.setString(String.format("%d%% complete ", progress));
    }

    /**
     * Set visibility of modal progress dialog
     *
     * @param visible defines stave of model window
     */
    @Override
    public void setVisible(final boolean visible) {

        SwingUtilities.invokeLater(() -> {

            if (visible) {
                // if modal is just invoked - set its progress to 0
                progressBar.setValue(0);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());

                // sleep for a while, for not to remove modal window just after 100% is set
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ProgressDialog.super.setVisible(visible);
        });
    }
}
