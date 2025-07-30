package org.example.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    public List<Persona> getAllPersons() throws SQLException {
        List<Persona> persons = new ArrayList<>();
        String sql = "SELECT * FROM PERSONE ORDER BY cognome, nome";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                persons.add(new Persona(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("indirizzo"),
                        rs.getString("telefono"),
                        rs.getInt("eta")
                ));
            }
        }
        return persons;
    }

    public void addPerson(Persona person) throws SQLException {
        String sql = "INSERT INTO PERSONE (nome, cognome, indirizzo, telefono, eta) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, person.getNome());
            pstmt.setString(2, person.getCognome());
            pstmt.setString(3, person.getIndirizzo());
            pstmt.setString(4, person.getTelefono());
            pstmt.setInt(5, person.getEta());
            pstmt.executeUpdate();
        }
    }

    public void updatePerson(Persona person) throws SQLException {
        String sql = "UPDATE PERSONE SET nome = ?, cognome = ?, indirizzo = ?, telefono = ?, eta = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, person.getNome());
            pstmt.setString(2, person.getCognome());
            pstmt.setString(3, person.getIndirizzo());
            pstmt.setString(4, person.getTelefono());
            pstmt.setInt(5, person.getEta());
            pstmt.setInt(6, person.getId());
            pstmt.executeUpdate();
        }
    }

    public void deletePerson(int id) throws SQLException {
        String sql = "DELETE FROM PERSONE WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
