package org.example.view;

import org.example.model.Persona;

import javax.swing.*;
import java.awt.*;

public class PersonaDialog extends JDialog {
    private JTextField nameField, surnameField, addressField, phoneField, ageField;
    private Persona person;
    private boolean succeeded;

    // Constructor for adding
    public PersonaDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initUI(null);
    }

    // Constructor for modifying
    public PersonaDialog(Frame parent, String title, boolean modal, Persona personToModify) {
        super(parent, title, modal);
        this.person = personToModify;
        initUI(personToModify);
    }

    private void initUI(Persona p) {
        succeeded = false;

        // Fields
        nameField = new JTextField(20);
        surnameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);
        ageField = new JTextField(5);

        if (p != null) {
            nameField.setText(p.getNome());
            surnameField.setText(p.getCognome());
            addressField.setText(p.getIndirizzo());
            phoneField.setText(p.getTelefono());
            ageField.setText(String.valueOf(p.getEta()));
        }

        // Layout
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(new JLabel("Nome:")); panel.add(nameField);
        panel.add(new JLabel("Cognome:")); panel.add(surnameField);
        panel.add(new JLabel("Indirizzo:")); panel.add(addressField);
        panel.add(new JLabel("Telefono:")); panel.add(phoneField);
        panel.add(new JLabel("Eta:")); panel.add(ageField);

        // Buttons
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> onOK());

        JButton cancelButton = new JButton("Cancella");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void onOK() {
        try {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            if (name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Cognome non possono essere vuoti.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int age = Integer.parseInt(ageField.getText().trim());

            if (person == null) { // Adding new person
                person = new Persona( name, surname, addressField.getText(), phoneField.getText(), age);
            } else { // Modifying existing person
                person.setNome(name);
                person.setCognome(surname);
                person.setIndirizzo(addressField.getText());
                person.setTelefono(phoneField.getText());
                person.setEta(age);
            }
            succeeded = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for age.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Persona getPerson() {
        return person;
    }

    public boolean isSucceeded() {
        return succeeded;
    }
}
