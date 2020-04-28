package model;

/**
 * Class provides data structure for tree's leaf
 */
public class ServerInfoModel {

    /**
     * object's (leaf's) id
     */
    private final int id;

    /**
     * object's (leaf's) name
     */
    private final String name;

    /**
     * identifies if object (leaf) is checked
     */
    private boolean checked;

    /**
     * Constructor provides setting up essential server info
     *
     * @param id      server's id
     * @param name    server's name
     * @param checked identifies if server is checked
     */
    public ServerInfoModel(int id, String name, boolean checked) {
        this.name = name;
        this.id = id;
        this.checked = checked;
    }

    /**
     * Return server's id
     *
     * @return server's id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns whether server is checked or not
     *
     * @return checked server's status
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Set server's checked status
     *
     * @param checked server's checked status
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * {@inheritDoc}
     *
     * @return ServerInfoModel string representation
     */
    @Override
    public String toString() {
        return name;
    }
}
