package gui.listeners;

/**
 * Interface for object,
 * that will preform action on accepting changes of form in Preferences Dialog
 */
public interface PrefsListener {

    /**
     * Sets preferences values
     *
     * @param user user name
     * @param password user password
     * @param port database port
     */
    void setPreferences(String user, String password, int port);
}
