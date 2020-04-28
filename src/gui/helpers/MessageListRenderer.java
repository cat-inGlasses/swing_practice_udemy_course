package gui.helpers;

import model.MessageModel;

import javax.swing.*;
import java.awt.*;

/**
 * Note -- this demonstrates using arbitrary components as list box renderer.
 * (Probably overkill in this case to use JPanel when JLabel could be use directly)
 */
public class MessageListRenderer implements ListCellRenderer {

    /**
     * Field used for panel which will be containing one element of list
     */
    private final JPanel panel;

    /**
     * Used for list element
     */
    private final JLabel label;

    /**
     * Used for selected color
     */
    private final Color selectedColor;

    /**
     * Used for normal color
     */
    private final Color normalColor;

    /**
     * Constructor
     * initialize colors, label and panel
     */
    public MessageListRenderer() {
        // setting colors
        selectedColor = new Color(210, 210, 255);
        normalColor = Color.WHITE;

        // setting Label
        label = new JLabel();
        try {
            label.setFont(Utils.createFont("/fonts/CrimewaveBB.ttf").deriveFont(Font.PLAIN, 20));
        } catch (NullPointerException e) {
            System.out.println("Unable to create font");
        }
        label.setIcon(Utils.createIcon("/images/Information24.png"));

        // setting panel
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
    }

    /**
     * {@inheritDoc}
     *
     * @param list
     * @param value
     * @param index
     * @param isSelected
     * @param cellHasFocus
     * @return
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        MessageModel message = (MessageModel) value;
        label.setText(message.getTitle());
        panel.setBackground(cellHasFocus ? selectedColor : normalColor);
        // label.setOpaque(true);

        return panel;
    }
}
