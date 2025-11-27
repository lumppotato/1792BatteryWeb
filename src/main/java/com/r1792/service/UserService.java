package com.r1792.service;

import com.r1792.model.User;
import com.r1792.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public void updatePassword(String username, String newPassword) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        repo.save(user);
    }


    public List<User> getAll() { return repo.findAll(); }

    public User get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public User save(User user) {
        // only encode if it’s a new password (not already hashed)
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return repo.save(user);
    }

    public void delete(Long id) { repo.deleteById(id); }

    // Automatically create default admin user
    @PostConstruct
    public void initDefaultAdmin() {
        repo.findByUsername("admin").ifPresentOrElse(
                u -> System.out.println("Admin user already exists"),
                () -> {
                    User admin = new User();
                    admin.setFirstName("Admin");
                    admin.setLastName("User");
                    admin.setUsername("admin");
                    admin.setRole("ADMIN");
                    admin.setPassword(passwordEncoder.encode("admin"));
                    repo.save(admin);
                    System.out.println("✅ Default admin user created: admin / admin");
                }
        );
    }

}