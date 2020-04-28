package gui.helpers;

import model.ServerInfoModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * Class is responsible to implement editing tree leaf
 */
public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

    /**
     * Renderer object
     */
    private final ServerTreeCellRenderer renderer;

    /**
     * Checkbox component, used for editor
     * Attention: this is one object per all leafs!
     */
    private JCheckBox checkBox;

    /**
     * Object with server data
     */
    private ServerInfoModel info;

    public ServerTreeCellEditor() {
        renderer = new ServerTreeCellRenderer();
    }

    /**
     * {@inheritDoc}
     *
     * <br><br>isCellEditable is called first before make tree cell editable
     *
     * @param e event object
     * @return if cell is editable
     */
    @Override
    public boolean isCellEditable(EventObject e) {

        // if it was not mouce evente (not clicking), i.e.: key stroke
        if (!(e instanceof MouseEvent)) {
            return false;
        }

        MouseEvent mouseEvent = (MouseEvent) e;
        JTree tree = (JTree) e.getSource();

        TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());

        if (path == null) return false;

        Object lastComponent = path.getLastPathComponent();

        if (lastComponent == null) return false;

        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastComponent;

        return treeNode.isLeaf();
    }

    /**
     * {@inheritDoc}
     *
     * @param tree
     * @param value
     * @param isSelected
     * @param expanded
     * @param leaf
     * @param row
     * @return
     */
    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected,
                                                boolean expanded, boolean leaf, int row) {

        Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);

        // if leaf set component editable
        if (leaf) {

            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            info = (ServerInfoModel) treeNode.getUserObject();

            checkBox = (JCheckBox) component;
            ItemListener itemListener = new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fireEditingStopped();
                    checkBox.removeItemListener(this);
                }
            };
            checkBox.addItemListener(itemListener);
        }

        return component;
    }

    /**
     * This method is called last, to set cell with value that was setted while editing
     *
     * @return {@link ServerInfoModel} object
     */
    @Override
    public Object getCellEditorValue() {
        info.setChecked(checkBox.isSelected());
        return info;
    }
}
