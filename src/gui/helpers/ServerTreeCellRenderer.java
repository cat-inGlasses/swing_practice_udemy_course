package gui.helpers;

import model.ServerInfoModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * Class is responsible for render view for each cell in Tree
 */
public class ServerTreeCellRenderer implements TreeCellRenderer {

    /**
     * object with represents how leaf will be rendered
     */
    private final JCheckBox leafRenderer;

    /**
     * object for non-leaf renderer
     */
    private final DefaultTreeCellRenderer nonLeafRenderer;

    /**
     * text color when item is not selected
     */
    private final Color textForeground;

    /**
     * background color when item is not selected
     */
    private final Color textBackground;

    /**
     * text color when item is selected
     */
    private final Color selectionForeground;

    /**
     * text color when item is not selected
     */
    private final Color selectionBackground;

    /**
     * Tree cell renderer constructor
     * Sets icons and colors
     */
    public ServerTreeCellRenderer() {
        // initialization of renderers
        leafRenderer = new JCheckBox();
        nonLeafRenderer = new DefaultTreeCellRenderer();

        // set icons for leaf, open and closed statuses
        nonLeafRenderer.setLeafIcon(Utils.createIcon("/images/Server16.png"));
        nonLeafRenderer.setOpenIcon(Utils.createIcon("/images/WebComponent16.png"));
        nonLeafRenderer.setClosedIcon(Utils.createIcon("/images/WebComponentAdd16.png"));

        // set colors
        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");
    }

    /**
     * {@inheritDoc}
     * @param tree
     * @param value
     * @param selected
     * @param expanded
     * @param leaf
     * @param row
     * @param hasFocus
     * @return
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        // if leaf create custom rendering
        if (leaf) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            ServerInfoModel nodeInfo = (ServerInfoModel) node.getUserObject(); // user object, that was passed to DefaultMutableTreeNode

            // set color depends on selection
            if (selected) {
                leafRenderer.setForeground(selectionForeground);
                leafRenderer.setBackground(selectionBackground);
            } else {
                leafRenderer.setForeground(textForeground);
                leafRenderer.setBackground(textBackground);
            }

            // set test and selection state
            leafRenderer.setText(nodeInfo.toString());
            leafRenderer.setSelected(nodeInfo.isChecked());

            return leafRenderer;
        } else {
            return nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, false, row, hasFocus);
        }
    }
}
