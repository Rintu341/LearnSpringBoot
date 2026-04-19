package com.sujan.repository;

import com.sujan.model.User;
import com.sujan.util.DatabaseConfig;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepository {

    // ─────────────────────────────────────────────────────────
    // SAVE — inserts a new user into the database
    // Returns the saved user WITH the generated id from DB
    // ─────────────────────────────────────────────────────────
    public User save(User user) throws SQLException {

        String sql = "INSERT INTO users (username, password, role) " +
                "VALUES (?, ?, ?) RETURNING id, created_at";

        // try-with-resources — auto closes connection after use
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // ? placeholders prevent SQL injection
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());  // already BCrypt hashed
            stmt.setString(3, user.getRole());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Set the DB-generated values back on the user object
                user.setId(rs.getInt("id"));
                user.setCreatedAt(rs.getTimestamp("created_at")
                        .toLocalDateTime());
            }
            return user;
        }
    }

    // ─────────────────────────────────────────────────────────
    // FIND BY USERNAME — used during login
    // Returns Optional — might be empty if user doesn't exist
    // ─────────────────────────────────────────────────────────
    public Optional<User> findByUsername(String username) throws SQLException {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Map database row → User object
                User user = mapRow(rs);
                return Optional.of(user);   // user found
            }
            return Optional.empty();        // user not found
        }
    }

    // ─────────────────────────────────────────────────────────
    // FIND BY ID — useful for profile/admin features later
    // ─────────────────────────────────────────────────────────
    public Optional<User> findById(int id) throws SQLException {

        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        }
    }

    // ─────────────────────────────────────────────────────────
    // EXISTS BY USERNAME — used during registration
    // Checks if username is already taken
    // ─────────────────────────────────────────────────────────
    public boolean existsByUsername(String username) throws SQLException {

        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;  // true if count > 0
            }
            return false;
        }
    }

    // ─────────────────────────────────────────────────────────
    // PRIVATE HELPER — maps a DB row to a User object
    // Used by findByUsername and findById
    // ─────────────────────────────────────────────────────────
    private User mapRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}