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

package com.learnify.api.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter that adds correlation identifiers to the Mapped Diagnostic Context (MDC).
 *
 * <p>This allows logging frameworks to include a unique request ID and request path
 * in every log entry, facilitating request tracing and debugging.
 *
 * @author Antonio C. Leite Jr
 * @version 1.0.0
 * @since 2026
 */
@Component
public class MDCFilter implements Filter {

    /**
     * Adds request-specific data to the MDC before processing the request.
     *
     * <p>Sets a unique {@code requestId} (UUID) and the request URI path.
     * The MDC is cleared after the request is handled to avoid cross-request pollution.
     *
     * @param request  the servlet request
     * @param response the servlet response
     * @param chain    the filter chain
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws
            IOException,
            ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("path", httpRequest.getRequestURI());

            chain.doFilter(request, response);
        }
        finally {
            MDC.clear();
        }
    }
}