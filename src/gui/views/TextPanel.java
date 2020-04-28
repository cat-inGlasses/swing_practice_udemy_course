package gui.views;

import javax.swing.*;
import java.awt.*;

/**
 * Component provides text panel to show server's message text
 */
public class TextPanel extends JPanel {

    /**
     * Field for textAres component
     */
    private final JTextArea textArea;

    /**
     * TextPanel Constructor
     */
    public TextPanel() {
        textArea = new JTextArea();

        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textArea.setFont(new Font("Serif", Font.PLAIN, 20));

        setLayout(new BorderLayout());
        // JScrollPane is needed to add scroll bars to the text area
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    /**
     * Method sets text in textAres component
     *
     * @param contents text to be set
     */
    public void setText(String contents) {
        textArea.setText(contents);
    }
}
