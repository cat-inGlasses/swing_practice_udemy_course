package gui.views;

import gui.helpers.FormEvent;
import gui.listeners.FormListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class is resposible for displaying view of Form panel and providing interactions with it
 */
public class FormPanel extends JPanel {

    /**
     * Field for Name label
     */
    private final JLabel nameLabel;

    /**
     * Field for providing name
     */
    private final JTextField nameField;

    /**
     * Field for occupation label
     */
    private final JLabel occupationLabel;

    /**
     * Field for providing occupation
     */
    private final JTextField occupationField;

    /**
     * Provides list for age Categories
     */
    private final JList<AgeCategory> ageList;

    /**
     * Provides combobox for employment type
     */
    private final JComboBox<String> empCombo;

    /**
     * Checkbox for determine if is citizen
     */
    private final JCheckBox citizenCheck;

    /**
     * Label for tax field
     */
    private final JLabel taxLabel;

    /**
     * Field for providing Tax id
     */
    private final JTextField taxField;

    /**
     * For radio group
     */
    private final ButtonGroup genderGroup;

    /**
     * For male radio button
     */
    private final JRadioButton maleRadio;

    /**
     * For female radio button
     */
    private final JRadioButton femaleRadio;

    /**
     * Submit form button
     */
    private final JButton okBtn;

    /**
     * Field for form listener
     */
    private FormListener formListener;

    public FormPanel() {

        // setting width of this panel, because layout automatically sets another
        Dimension dim = getPreferredSize();
        dim.width = 250;
        setPreferredSize(dim);
        setMinimumSize(dim);

        // set Name
        nameLabel = new JLabel("Name: ");
        nameField = new JTextField(10);

        // set Occupation
        occupationLabel = new JLabel("Occupation: ");
        occupationField = new JTextField(10);

        // set age
        ageList = new JList<>();
        setAgeList();

        // set Employment
        empCombo = new JComboBox<>();
        setEmpCombo();

        // Set Citizen and Tax
        citizenCheck = new JCheckBox();
        taxLabel = new JLabel("Tax ID: ");
        taxField = new JTextField(10);
        setTax();

        // set Gender group
        genderGroup = new ButtonGroup();
        maleRadio = new JRadioButton("male");
        femaleRadio = new JRadioButton("female");
        setGender();

        // submit button
        okBtn = new JButton("OK");

        // set up mnemonics
        okBtn.setMnemonic(KeyEvent.VK_O);
        nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nameLabel.setLabelFor(nameField);

        setListeners();

        setBorder();

        settingLayout();
    }

    /**
     * Method provides setting age List
     */
    private void setAgeList() {
        // setup list box
        DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
        ageModel.addElement(new AgeCategory(0, "Under 18"));
        ageModel.addElement(new AgeCategory(1, "18 to 65"));
        ageModel.addElement(new AgeCategory(2, "65 and over"));
        ageList.setModel(ageModel);
        ageList.setPreferredSize(new Dimension(120, 66));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(1);
    }

    /**
     * Method provides setting Employment combobox
     */
    private void setEmpCombo() {
        DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<>();
        empModel.addElement("employed");
        empModel.addElement("self-employed");
        empModel.addElement("unemployed");
        empCombo.setModel(empModel);
        empCombo.setSelectedIndex(0);
        empCombo.setEditable(true);
    }

    /**
     * Method provides setting tax initialization
     */
    public void setTax() {
        taxLabel.setEnabled(false);
        taxField.setEnabled(false);
    }

    /**
     * Method provides setting Gender
     */
    public void setGender() {
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        maleRadio.setActionCommand("male");
        femaleRadio.setActionCommand("female");
    }

    private void setListeners() {
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String occupation = occupationField.getText();
                AgeCategory ageCat = ageList.getSelectedValue();
                String empCat = (String) empCombo.getSelectedItem();
                String taxId = taxField.getText();
                boolean usCitizen = citizenCheck.isSelected();

                String gender = genderGroup.getSelection().getActionCommand();

                // send
                FormEvent ev = new FormEvent(this, name, occupation,
                        ageCat.getId(), empCat, taxId, usCitizen, gender);

                if (formListener != null) {
                    formListener.formEventOccurred(ev);
                }
            }
        });

        citizenCheck.addActionListener(e -> {
            boolean isTicked = citizenCheck.isSelected();
            taxLabel.setEnabled(isTicked);
            taxField.setEnabled(isTicked);
        });
    }

    /**
     * Sets border
     */
    private void setBorder() {
        // Setting boarders for this left panel
        // one outer for margins and one inner for titling
        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    /**
     * Sets layout
     */
    private void settingLayout() {

        setLayout(new GridBagLayout());

        // object for setting parameters for this layout
        GridBagConstraints gc = new GridBagConstraints();

        // do not resize components
        gc.fill = GridBagConstraints.NONE;

        // Name line
        gc.gridy = 0;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(nameLabel, gc);

        gc.gridy = 0;
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gc);

        // Occupation line
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(occupationLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(occupationField, gc);

        // List
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Age"), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 5, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ageList, gc);

        // List
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Type"), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(empCombo, gc);

        // Checkboxes
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("US Citizen: "), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(citizenCheck, gc);

        // tax section
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(taxLabel, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(taxField, gc);

        // Radio group section
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0, 0, 0, 5);
        add(new JLabel("Gender: "), gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(maleRadio, gc);

        // femail
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 1;

        gc.gridx = 1;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(femaleRadio, gc);

        // Ok Button
        gc.gridy++;

        gc.weightx = 100;
        gc.weighty = 200;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(okBtn, gc);
    }

    /**
     * Sets form listener
     * @param formListener {@link FormListener} object
     */
    public void setFormListener(FormListener formListener) {
        this.formListener = formListener;
    }
}

/**
 * Class is responsible for containig caluse for age categories
 */
class AgeCategory {

    /**
     * Age Id
     */
    private final int id;

    /**
     * Age description text
     */
    private final String text;

    /**
     * Constructor
     * field initialization
     *
     * @param id   age ID
     * @param text age description text
     */
    public AgeCategory(int id, String text) {
        this.id = id;
        this.text = text;
    }

    /**
     * {@inheritDoc}
     *
     * @return AgeCategory string representation
     */
    @Override
    public String toString() {
        return text;
    }

    /**
     * Getter for {@link AgeCategory#id}
     *
     * @return {@link AgeCategory#id}
     */
    public int getId() {
        return id;
    }
}