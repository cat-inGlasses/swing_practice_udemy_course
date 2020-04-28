package gui.views;

import controller.MessageServer;
import gui.dialogs.ProgressDialog;
import gui.helpers.MessageListRenderer;
import gui.helpers.ServerTreeCellEditor;
import gui.helpers.ServerTreeCellRenderer;
import gui.listeners.ProgressDialogListener;
import model.MessageModel;
import model.ServerInfoModel;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Class is responsible for show server tree, and messages from each server
 */
public class MessagePanel extends JPanel implements ProgressDialogListener {

    /**
     * Field holds object of servers' tree
     */
    private final JTree serverTree;

    /**
     * Field holds tree cell renderer
     */
    private final ServerTreeCellRenderer treeCellRenderer;

    /**
     * Field holds tree cell editor
     */
    private final ServerTreeCellEditor treeCellEditor;

    /**
     * Field for object to hold ids of selected servers
     */
    private final Set<Integer> selectedServers;

    /**
     * Field for {@link MessageServer} controller
     */
    private final MessageServer messageServer;

    /**
     * field for loading modal component
     */
    private final ProgressDialog progressDialog;

    /**
     * Field for SwingWorker (load data in other thread)
     */
    private SwingWorker<List<MessageModel>, Integer> worker;

    /**
     * Filed for list of {@link MessageModel}s
     */
    private final JList<MessageModel> messageList;

    /**
     * Field for object holding message model
     */
    private final DefaultListModel<MessageModel> messageListModel;

    /**
     * Filed for textPanel Object. Used for displaying text from message
     */
    private final TextPanel textPanel;

    /**
     * Field for object with server's tree
     */
    private final JSplitPane upperPane;

    /**
     * Field for object with server's messages
     */
    private final JSplitPane lowerPane;

    public MessagePanel(JFrame parent) {

        // initialise contoller
        messageServer = new MessageServer();
        selectedServers = new TreeSet<>();
        setDefaultServers(0, 1, 4);
        messageServer.setSelectedServers(selectedServers);

        // set progress Dialog
        progressDialog = new ProgressDialog(parent, "Messages Downloading...");

        // set renderer and editor for tree
        treeCellRenderer = new ServerTreeCellRenderer(); // set renderer for leaf
        treeCellEditor = new ServerTreeCellEditor(); // set editor for leaf
        serverTree = new JTree(createTree());
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setCellEditor(treeCellEditor);
        serverTree.setEditable(true);
        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // setting message List
        messageListModel = new DefaultListModel<>();
        messageList = new JList<>(messageListModel);
        messageList.setMinimumSize(new Dimension(10/*doesn't matter*/, 100));
        messageList.setCellRenderer(new MessageListRenderer());

        // setting TextPanel
        textPanel = new TextPanel();
        textPanel.setMinimumSize(new Dimension(10/*doesn't matter*/, 100));

        // positioning tree, message list and text panel
        lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(messageList), textPanel);
        upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree), lowerPane);

        // set proportions for upper and lower panes
        upperPane.setResizeWeight(0.5);
        lowerPane.setResizeWeight(0.5);

        setListeners();

        setLayout(new BorderLayout());
        add(upperPane, BorderLayout.CENTER);
    }

    /**
     * Methods sets listeners
     * <ul>
     *     <li>set {@code this} for progressDialog listener</li>
     *     <li>set listener on stop editing tree cell - causes messages to be reload</li>
     *     <li>set listener for message list selection</li>
     * </ul>
     */
    public void setListeners() {

        progressDialog.setListener(this);

        treeCellEditor.addCellEditorListener(new CellEditorListener() {

            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfoModel info = (ServerInfoModel) treeCellEditor.getCellEditorValue();

                int serverId = info.getId();
                if (info.isChecked()) {
                    selectedServers.add(serverId);
                } else {
                    selectedServers.remove(serverId);
                }

                messageServer.setSelectedServers(selectedServers);

                retrieveMessages();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        });

        messageList.addListSelectionListener(e -> {
            MessageModel message = messageList.getSelectedValue();
            textPanel.setText((message != null) ? message.getContents() : "");
        });
    }

    /**
     * Set with servers will be checked by default
     *
     * @param servers servers' ids
     */
    private void setDefaultServers(Integer... servers) {
        selectedServers.addAll(Arrays.asList(servers));
    }

    /**
     * Method forces messages to be retrieved once more
     */
    public void refresh() {
        retrieveMessages();
    }

    /**
     * Method retrieves data from selected servers
     */
    public void retrieveMessages() {
        progressDialog.setMaximum(messageServer.getMessageCount());
        progressDialog.setVisible(true);

        worker = new SwingWorker<>() {
            // retrieving
            @Override
            protected List<MessageModel> doInBackground() {

                List<MessageModel> retrieveMessages = new ArrayList<>();

                int count = 0;

                for (MessageModel message : messageServer) {
                    if (isCancelled()) break;
                    retrieveMessages.add(message);
                    count++;
                    publish(count);
                }
                return retrieveMessages;
            }

            // update loading dialog progress
            @Override
            protected void process(List<Integer> counts) {
                int retrieved = counts.get(counts.size() - 1);
                progressDialog.setValue(retrieved);
            }

            // action on completion
            @Override
            protected void done() {

                progressDialog.setVisible(false);

                if (isCancelled()) return;

                try {
                    List<MessageModel> retrievedMessages = get();

                    messageListModel.clear();
                    for (MessageModel message : retrievedMessages) {
                        messageListModel.addElement(message);
                    }

                    // set default choosed elem
                    messageList.setSelectedIndex(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };

        worker.execute();
    }

    /**
     * Method constructs servers' tree
     *
     * @return {@link DefaultMutableTreeNode} object with settled tree structure
     */
    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfoModel(0, "New York", selectedServers.contains(0)));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfoModel(1, "Boston", selectedServers.contains(1)));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfoModel(2, "Los Angeles", selectedServers.contains(2)));
        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfoModel(3, "London", selectedServers.contains(3)));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfoModel(4, "Edinburgh", selectedServers.contains(4)));
        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);

        return top;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelLoading() {
        if (worker != null) {
            worker.cancel(true);
        }
    }
}