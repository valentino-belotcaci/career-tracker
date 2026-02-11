package VaLocaProject.Security;

import java.io.IOException;

import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // FIX: Add a null check here
        if (csrfToken != null) {
            // This triggers the cookie generation
            csrfToken.getToken(); 
        }

        filterChain.doFilter(request, response);
    }
}