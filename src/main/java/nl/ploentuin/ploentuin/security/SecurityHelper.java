package nl.ploentuin.ploentuin.security;

import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {

    private final UserRepository userRepository;

    public SecurityHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCurrentUser(int userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;

        String username = auth.getName();
        User user = userRepository.findByUsernameIgnoreCase(username).orElse(null);
        return user != null && user.getId() == userId;
    }
}