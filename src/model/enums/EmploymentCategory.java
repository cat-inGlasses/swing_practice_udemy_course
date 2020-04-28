package model.enums;

/**
 * Enum to represent employment categories
 */
public enum EmploymentCategory {
    employed("emploeed"),
    selfEmployed("self employed"),
    unemployed("unemployed"),
    other("other");

    /**
     * Field represents constant enum with string text
     */
    private final String text;

    EmploymentCategory(String text) {
        this.text = text;
    }

    /**
     * {@inheritDoc}
     *
     * @return text of constant
     */
    @Override
    public String toString() {
        return text;
    }
}
