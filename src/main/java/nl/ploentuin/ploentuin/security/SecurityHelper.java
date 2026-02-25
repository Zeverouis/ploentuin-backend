package nl.ploentuin.ploentuin.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {

    public boolean isCurrentUsername(String username) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return currentUsername.equalsIgnoreCase(username);
    }

    public boolean isCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}