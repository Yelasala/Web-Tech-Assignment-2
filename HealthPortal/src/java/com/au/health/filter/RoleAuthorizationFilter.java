package com.au.health.filter;

import com.au.health.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RoleAuthorizationFilter", urlPatterns = {"/staff_portal.jsp"})
public class RoleAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute("authenticatedUser");
            if (user != null && "PATIENT".equals(user.getRole())) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Patients are not authorized to view the Staff Portal.");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}