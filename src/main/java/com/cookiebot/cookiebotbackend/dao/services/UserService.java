package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.User;
import com.cookiebot.cookiebotbackend.dao.repository.UserRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }

    public User insert(User user) {
        try {
            return repository.save(user);
        } catch (DuplicateKeyException e) {
            throw new BadRequestException("User with this ID already exists");
        }

    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ObjectNotFoundException("User not found");
        }
        repository.deleteById(id);
    }

    public User update(String id, User user) {
        User existingUser = findById(id);
        updateUserData(existingUser, user);
        return repository.save(existingUser);
    }

    private void updateUserData(User existingUser, User user) {
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getLanguageCode() != null) {
            existingUser.setLanguageCode(user.getLanguageCode());
        }
        if (user.getBirthdate() != null) {
            existingUser.setBirthdate(user.getBirthdate());
        }
    }
}
