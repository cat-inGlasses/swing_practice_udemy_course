package gui.listeners;

/**
 * Interface for object,
 * that will preform action interrupting data loading (while progress dialog is visible
 */
public interface ProgressDialogListener {

    /**
     * Function cancel loading data
     */
    void cancelLoading();
}
