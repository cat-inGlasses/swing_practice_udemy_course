package gui.listeners;

/**
 * Interface for object,
 * that will preform action on button clicking in toolbar menu (while progress dialog is visible
 */
public interface ToolbarListener {

    /**
     *  Method should implement what should be done when <b>save</b> event occurs
     */
    void saveEventOccurred();

    /**
     *  Method should implement what should be done when <b>refresh</b> event occurs
     */
    void refreshEventOccurred();
}
