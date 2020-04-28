package model;

import model.enums.AgeCategory;
import model.enums.EmploymentCategory;
import model.enums.Gender;

import java.io.Serializable;

/**
 * Person model - stores Person data
 */
public class PersonModel implements Serializable {

    private static final long serialVersionUID = -8219218627533074108L;
    private static int count = 1;

    /**
     * person's id
     */
    private int id;

    /**
     * person's name
     */
    private String name;

    /**
     * person's occupation
     */
    private String occupation;

    /**
     * person's age category
     */
    private AgeCategory ageCategory;

    /**
     * person's employment category
     */
    private EmploymentCategory empCat;

    /**
     * person's tax ID
     */
    private String taxId;

    /**
     * if person is US Citizen
     */
    private boolean usCitizen;

    /**
     * person's gender
     */
    private Gender gender;

    /**
     * Constructor.
     *
     * @param name        represents {@link PersonModel#name}
     * @param occupation  represents {@link PersonModel#occupation}
     * @param ageCategory represents {@link PersonModel#ageCategory}
     * @param empCat      represents {@link PersonModel#empCat}
     * @param taxId       represents {@link PersonModel#taxId}
     * @param usCitizen   represents {@link PersonModel#usCitizen}
     * @param gender      represents {@link PersonModel#gender}
     */
    public PersonModel(String name, String occupation, AgeCategory ageCategory,
                       EmploymentCategory empCat, String taxId,
                       boolean usCitizen, Gender gender) {
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;

        this.id = count++;
    }

    /**
     * Constructor
     *
     * @param id          represents {@link PersonModel#id}
     * @param name        represents {@link PersonModel#name}
     * @param occupation  represents {@link PersonModel#occupation}
     * @param ageCategory represents {@link PersonModel#ageCategory}
     * @param empCat      represents {@link PersonModel#empCat}
     * @param taxId       represents {@link PersonModel#taxId}
     * @param usCitizen   represents {@link PersonModel#usCitizen}
     * @param gender      represents {@link PersonModel#gender}
     */
    public PersonModel(int id, String name, String occupation, AgeCategory ageCategory,
                       EmploymentCategory empCat, String taxId,
                       boolean usCitizen, Gender gender) {
        this(name, occupation, ageCategory, empCat, taxId, usCitizen, gender);
        this.id = id;
    }

    /**
     * Method returns builder object
     *
     * @return {@link PersonBuilder} object
     */
    public static PersonBuilder getBuilder() {
        return new PersonBuilder();
    }

    /**
     * Builder, to provide build steps for the {@link PersonModel} model object
     */
    public static class PersonBuilder {

        /**
         * represents {@link PersonModel#name}
         */
        private String name;

        /**
         * represents {@link PersonModel#occupation}
         */
        private String occupation;

        /**
         * represents {@link PersonModel#ageCategory}
         */
        private AgeCategory ageCategory;

        /**
         * represents {@link PersonModel#empCat}
         */
        private EmploymentCategory empCat;

        /**
         * represents {@link PersonModel#taxId}
         */
        private String taxId;

        /**
         * represents {@link PersonModel#usCitizen}
         */
        private boolean usCitizen;

        /**
         * represents {@link PersonModel#gender}
         */
        private Gender gender;

        /**
         * Set buffer person's name to {@link PersonBuilder#name}
         *
         * @param name string for name field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set buffer person's occupation to {@link PersonBuilder#occupation}
         *
         * @param occupation string for {@link PersonBuilder#occupation} field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withOccupation(String occupation) {
            this.occupation = occupation;
            return this;
        }

        /**
         * Set buffer person's ageCategory to {@link PersonBuilder#ageCategory}
         *
         * @param ageCategory int for {@link PersonBuilder#ageCategory} field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withCategory(int ageCategory) {
            switch (ageCategory) {
                case 0:
                    this.ageCategory = AgeCategory.child;
                    break;
                case 1:
                    this.ageCategory = AgeCategory.adult;
                    break;
                case 2:
                    this.ageCategory = AgeCategory.senior;
                    break;
            }
            return this;
        }

        /**
         * Set buffer person's empCat to {@link PersonBuilder#empCat}
         *
         * @param empCat int for {@link PersonBuilder#empCat} field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withEmployment(String empCat) {

            if (empCat.equals("employed")) {
                this.empCat = EmploymentCategory.employed;
            } else if (empCat.equals("self-employed")) {
                this.empCat = EmploymentCategory.selfEmployed;
            } else if (empCat.equals("unemployed")) {
                this.empCat = EmploymentCategory.unemployed;
            } else {
                this.empCat = EmploymentCategory.other;
            }

            return this;
        }

        /**
         * Set buffer person's usCitizen to {@link PersonBuilder#usCitizen}
         *
         * @param usCitizen boolean for {@link PersonBuilder#usCitizen} field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withUSCitizen(boolean usCitizen) {

            this.usCitizen = usCitizen;

            return this;
        }

        /**
         * Set buffer person's usCitizen to {@link PersonBuilder#usCitizen}
         * and person's taxId to {@link PersonBuilder#taxId}
         *
         * @param usCitizen boolean for {@link PersonBuilder#usCitizen} field
         * @param taxId     string for {@link PersonBuilder#taxId} field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withUSCitizen(boolean usCitizen, String taxId) {

            this.taxId = taxId;
            this.usCitizen = usCitizen;

            return this;
        }

        /**
         * Set buffer person's gender to {@link PersonBuilder#gender}
         *
         * @param gender boolean for {@link PersonBuilder#gender} field
         * @return {@link PersonBuilder} instance
         */
        public PersonBuilder withGender(String gender) {

            this.gender = gender.equals("male") ? Gender.male : Gender.female;

            return this;
        }

        /**
         * Builds person object with buffered values
         *
         * @return {@link PersonModel} object
         */
        public PersonModel build() {
            return new PersonModel(this.name, this.occupation, this.ageCategory, this.empCat,
                    this.taxId, this.usCitizen, this.gender);
        }
    }

    /**
     * Getter, returns person's id
     *
     * @return {@link PersonModel#id}
     */
    public int getId() {
        return id;
    }

    /**
     * Getter, returns person's id
     *
     * @return {@link PersonModel#id}
     */
    public String getName() {
        return name;
    }

    /**
     * Getter, returns person's occupation
     *
     * @return {@link PersonModel#occupation}
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Getter, returns person's ageCategory
     *
     * @return {@link PersonModel#ageCategory}
     */
    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    /**
     * Getter, returns person's empCat
     *
     * @return {@link PersonModel#empCat}
     */
    public EmploymentCategory getEmpCat() {
        return empCat;
    }

    /**
     * Getter, returns person's taxId
     *
     * @return {@link PersonModel#taxId}
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Getter, returns if person is usCitizen
     *
     * @return {@link PersonModel#usCitizen}
     */
    public boolean isUsCitizen() {
        return usCitizen;
    }

    /**
     * Getter, returns person's gender
     *
     * @return {@link PersonModel#gender}
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#name} to given value
     *
     * @param name {@link String} object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#occupation} to given value
     *
     * @param occupation {@link String} object
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#ageCategory} to given value
     *
     * @param ageCategory {@link AgeCategory} object
     */
    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#empCat} to given value
     *
     * @param empCat {@link EmploymentCategory} object
     */
    public void setEmpCat(EmploymentCategory empCat) {
        this.empCat = empCat;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#taxId} to given value
     *
     * @param taxId {@link String} object
     */
    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#usCitizen} to given value
     *
     * @param usCitizen boolean value
     */
    public void setUsCitizen(boolean usCitizen) {
        this.usCitizen = usCitizen;
    }

    /**
     * Setter. Set's Person's {@link PersonModel#gender} to given value
     *
     * @param gender {@link Gender} object
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * {@inheritDoc}
     *
     * @return Person string representation
     */
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", occupation='" + occupation + '\'' +
                ", ageCategory=" + ageCategory +
                ", empCat=" + empCat +
                ", taxId='" + taxId + '\'' +
                ", usCitizen=" + usCitizen +
                ", gender=" + gender +
                '}';
    }
}
