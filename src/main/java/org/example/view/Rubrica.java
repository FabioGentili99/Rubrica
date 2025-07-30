package org.example.view;

import org.example.model.Persona;
import org.example.model.PersonaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Rubrica extends JFrame{
    private final PersonaDAO personDAO;
    private JTable table;
    private PersonaTableModel tableModel;

    public Rubrica() {
        personDAO = new PersonaDAO();

        setTitle("Rubrica");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        tableModel = new PersonaTableModel(new ArrayList<>()); // Start with empty list
        table = new JTable(tableModel);

        // Buttons
        JButton addButton = new JButton("Aggiungi");
        JButton modifyButton = new JButton("Modifica");
        JButton deleteButton = new JButton("Elimina");

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addPerson());
        modifyButton.addActionListener(e -> modifyPerson());
        deleteButton.addActionListener(e -> deletePerson());

        refreshTable();
    }

    private void refreshTable() {
        try {
            tableModel = new PersonaTableModel(personDAO.getAllPersons());
            table.setModel(tableModel);
        } catch (SQLException e) {
            showError("Error loading data from database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addPerson() {
        PersonaDialog dialog = new PersonaDialog(this, "Aggiungi Persona", true);
        dialog.setVisible(true);

        if (dialog.isSucceeded()) {
            try {
                personDAO.addPerson(dialog.getPerson());
                refreshTable();
            } catch (SQLException e) {
                showError("Error adding person: " + e.getMessage());
            }
        }
    }

    private void modifyPerson() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Persona personToModify = tableModel.getPersonAt(selectedRow);

            PersonaDialog dialog = new PersonaDialog(this, "Modifica Persona", true, personToModify);
            dialog.setVisible(true);

            if (dialog.isSucceeded()) {
                try {
                    personDAO.updatePerson(dialog.getPerson());
                    refreshTable();
                } catch (SQLException e) {
                    showError("Error modifying person: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deletePerson() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            Persona personToDelete = tableModel.getPersonAt(selectedRow);
            int choice = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler eliminare " + personToDelete.getNome() + " " + personToDelete.getCognome() + "?",
                    "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    personDAO.deletePerson(personToDelete.getId());
                    refreshTable();
                } catch (SQLException e) {
                    showError("Error deleting person: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleziona una persona da eliminare.", "Nessuna Selezione", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Database Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Rubrica().setVisible(true));
    }
}
