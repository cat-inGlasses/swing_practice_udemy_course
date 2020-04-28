package model;

/**
 * Class provides model for server Message
 */
public class MessageModel {

    /**
     * Message title
     */
    private String title;

    /**
     * Message Contents
     */
    private String contents;

    /**
     * Constructor. Provides fields initialization
     *
     * @param title    {@link MessageModel#title}
     * @param contents {@link MessageModel#contents}
     */
    public MessageModel(String title, String contents) {
        super();
        this.title = title;
        this.contents = contents;
    }

    /**
     * Getter for {@link MessageModel#title}
     *
     * @return {@link MessageModel#title}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for {@link MessageModel#title}
     *
     * @param title value for field {@link MessageModel#title}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for {@link MessageModel#contents}
     *
     * @return {@link MessageModel#contents}
     */
    public String getContents() {
        return contents;
    }

    /**
     * Setter for {@link MessageModel#contents}
     *
     * @param contents value for field {@link MessageModel#contents}
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
