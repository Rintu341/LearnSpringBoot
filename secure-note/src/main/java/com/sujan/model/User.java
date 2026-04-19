package com.sujan.model;

import java.time.LocalDateTime;

public class User {

    private int id;
    private String username;
    private String password;    // stores BCrypt hash, never plain text
    private String role;
    private LocalDateTime createdAt;

    // ─── Constructors ───────────────────────────────────────

    // Empty constructor — needed by Jackson for JSON conversion
    public User() {}

    // Constructor for creating new user (no id yet — DB generates it)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Full constructor — used when reading from DB
    public User(int id, String username, String password,
                String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    // ─── Getters ────────────────────────────────────────────

    public int getId()                  { return id; }
    public String getUsername()         { return username; }
    public String getPassword()         { return password; }
    public String getRole()             { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ─── Setters ────────────────────────────────────────────

    public void setId(int id)                       { this.id = id; }
    public void setUsername(String username)         { this.username = username; }
    public void setPassword(String password)         { this.password = password; }
    public void setRole(String role)                 { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt){ this.createdAt = createdAt; }

    // ─── toString — helpful for debugging ───────────────────
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                '}';
        // ⚠️ Never include password in toString!
    }
}