/*
 * Copyright (c) 2026 Antonio C. Leite Jr
 *
 * Learnify
 *
 * Final Year Project (Academic Capstone)
 * Bachelor in Software Engineering
 *
 * All rights reserved.
 */

package com.learnify.api.security;

import com.learnify.api.domain.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Custom implementation of {@link UserDetails} that wraps a {@link Usuario} entity.
 *
 * <p>This class bridges the application's user domain model with Spring Security's
 * authentication framework, providing the necessary user information and authorities.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    /**
     * Constructs a new CustomUserDetails instance with the given user.
     *
     * @param usuario the user entity to be wrapped
     */
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Returns the user's unique identifier.
     *
     * @return the user ID as {@link UUID}
     */
    public UUID getId() {
        return usuario.getId();
    }

    /**
     * Returns the user's email address.
     *
     * <p>This is the principal used for authentication.
     *
     * @return the user email
     */
    public String getEmail() {
        return usuario.getEmail();
    }

    /**
     * Returns the underlying {@link Usuario} entity.
     *
     * <p>Provides access to the full user object when needed in business logic.
     *
     * @return the {@code Usuario} entity
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * <p>Currently, a single authority is assigned based on the user's role,
     * prefixed with "ROLE_" (e.g., ROLE_ESTUDANTE).
     *
     * @return a collection of {@link GrantedAuthority} representing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name()));
    }

    /**
     * Returns the user's encrypted password.
     *
     * @return the password hash stored in the database
     */
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    /**
     * Returns the username used for authentication (the email).
     *
     * @return the user's email address
     */
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    /**
     * Indicates whether the user's account has not expired.
     *
     * <p>Always returns {@code true} as account expiration is not implemented.
     *
     * @return {@code true}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is not locked.
     *
     * <p>Always returns {@code true} as account locking is not implemented.
     *
     * @return {@code true}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have not expired.
     *
     * <p>Always returns {@code true} as credential expiration is not implemented.
     *
     * @return {@code true}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled (active).
     *
     * <p>Always returns {@code true} as user activation is not implemented.
     *
     * @return {@code true}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}