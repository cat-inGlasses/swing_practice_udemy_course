package gui.listeners;

/**
 * Interface for object,
 * that will preform action on table in Table Pane
 */
public interface PersonTableListener {

    /**
     * Method to preform table's row deleting (from model)
     *
     * @param row row's id
     */
    void rowDeleted(int row);
}
