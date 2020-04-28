package gui.helpers;

import gui.views.FormPanel;

import java.util.EventObject;

/**
 * Class provides untieing of From class from controller
 * with it From class can send data
 */
public class FormEvent extends EventObject {

    /**
     * Field for {@link FormPanel#nameField}
     */
    private final String name;

    /**
     * Field for {@link FormPanel#occupationField}
     */
    private final String occupation;

    /**
     * Field for {@link FormPanel#empCombo} selected value
     */
    private final String empCat;

    /**
     * Field for {@link FormPanel#ageList} selected value
     */
    private final int ageCategory;

    /**
     * Field for {@link FormPanel#taxField} value
     */
    private final String taxId;

    /**
     * Field for {@link FormPanel#citizenCheck} checkbox
     */
    private final boolean usCitizen;

    /**
     * Field for {@link FormPanel#genderGroup} selected radiobutton
     */
    private final String gender;

    /**
     * Constructs a prototypical Event.
     *
     * @param source      the object on which the Event initially occurred
     * @param name        name from {@link FormPanel#nameField}
     * @param occupation  occupation from {@link FormPanel#occupationField}
     * @param ageCategory age category from {@link FormPanel#ageList}
     * @param empCat      employment category from {@link FormPanel#empCombo}
     * @param taxId       tax ID from {@link FormPanel#taxField}
     * @param usCitizen   us Citizen from {@link FormPanel#citizenCheck}
     * @param gender      gender from {@link FormPanel#genderGroup}
     */
    public FormEvent(Object source, String name, String occupation, int ageCategory,
                     String empCat, String taxId, boolean usCitizen, String gender) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;
    }

    /**
     * Getter for {@link FormEvent#name}
     *
     * @return {@link FormEvent#name}
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for {@link FormEvent#occupation}
     *
     * @return {@link FormEvent#occupation}
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Getter for {@link FormEvent#ageCategory}
     *
     * @return {@link FormEvent#ageCategory}
     */
    public int getAgeCategory() {
        return ageCategory;
    }

    /**
     * Getter for {@link FormEvent#empCat}
     *
     * @return {@link FormEvent#empCat}
     */
    public String getEmploymentCategory() {
        return empCat;
    }

    /**
     * Getter for {@link FormEvent#taxId}
     *
     * @return {@link FormEvent#taxId}
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Getter for {@link FormEvent#usCitizen}
     *
     * @return {@link FormEvent#usCitizen}
     */
    public boolean isUsCitizen() {
        return usCitizen;
    }

    /**
     * Getter for {@link FormEvent#gender}
     *
     * @return {@link FormEvent#gender}
     */
    public String getGender() {
        return gender;
    }
}
