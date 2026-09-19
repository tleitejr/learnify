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

package com.learnify.api.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Request DTO for user login.
 *
 * <p>Contains the credentials (email and password) needed to authenticate
 * a user and obtain a JWT token.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Getter
@Setter
public class LoginRequestDTO {

    String email;
    String senha;
}