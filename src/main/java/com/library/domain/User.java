package com.library.domain;

/**
 * A user of the library system.
 */
public record User(String username, String passwordHash, Role role, boolean active, boolean mustChangePassword) {
}