package org.example.view;

import org.example.model.Persona;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonaTableModel extends AbstractTableModel  {
    private final String[] columnNames = {"Nome", "Cognome", "Telefono"};
    private List<Persona> personList;

    public PersonaTableModel(List<Persona> personList) {
        this.personList = personList;
    }

    @Override
    public int getRowCount() {
        return personList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona person = personList.get(rowIndex);
        switch (columnIndex) {
            case 0: return person.getNome();
            case 1: return person.getCognome();
            case 2: return person.getTelefono();
            default: return null;
        }
    }

    public Persona getPersonAt(int rowIndex) {
        return personList.get(rowIndex);
    }
}
