package dao;

import model.Registration;
import connector.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class RegistrationDAO {
    public void addRegistration(Registration registration) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Registration (name, email, date_of_birth, phone) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, registration.getName());
                stmt.setString(2, registration.getEmail());
                stmt.setDate(3, Date.valueOf(registration.getDateOfBirth()));
                stmt.setString(4, registration.getPhone());
                stmt.executeUpdate();
            }
        }
    }

    public List<Registration> getAllRegistrations() throws Exception {
        List<Registration> registrationslst = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Registration";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Registration reg = new Registration();
                        reg.setId(rs.getInt("id"));
                        reg.setName(rs.getString("name"));
                        reg.setEmail(rs.getString("email"));
                        reg.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                        reg.setPhone(rs.getString("phone"));
                        registrationslst.add(reg);
                    }
                }
            }
        }
        return registrationslst;
    }

    public Registration getRegistrationById(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Registration WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Registration reg = new Registration();
                        reg.setId(rs.getInt("id"));
                        reg.setName(rs.getString("name"));
                        reg.setEmail(rs.getString("email"));
                        reg.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                        reg.setPhone(rs.getString("phone"));
                        return reg;
                    }
                }
            }
        }
        throw new Exception("Registration not found");
    }

    public void updateRegistration(Registration registration) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Registration SET name = ?, email = ?, date_of_birth = ?, phone = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, registration.getName());
                stmt.setString(2, registration.getEmail());
                stmt.setDate(3, Date.valueOf(registration.getDateOfBirth()));
                stmt.setString(4, registration.getPhone());
                stmt.setInt(5, registration.getId());
                stmt.executeUpdate();
            }
        }
    }

    public void deleteRegistration(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Registration WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
        }
    }
}
