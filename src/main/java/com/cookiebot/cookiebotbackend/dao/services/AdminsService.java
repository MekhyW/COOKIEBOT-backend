package com.cookiebot.cookiebotbackend.dao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cookiebot.cookiebotbackend.core.domains.Admins;
import com.cookiebot.cookiebotbackend.dao.repository.AdminsRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminsService {

    private final AdminsRepository repository;

    @Autowired
    public AdminsService(AdminsRepository repository) {
        this.repository = repository;
    }

    public List<Admins> findAll() {
        return repository.findAll();
    }

    public Admins findByGroupId(String groupId) {
        return repository.findById(groupId)
                .orElseThrow(() -> new ObjectNotFoundException("Admins not found for group: " + groupId));
    }

    public Admins insert(Admins admins) {
        if (repository.existsById(admins.getGroupId())) {
            throw new BadRequestException("Admins already exist for group: " + admins.getGroupId());
        }
        return repository.save(admins);
    }

    public Admins update(String groupId, Admins admins) {
        Admins existingAdmins = findByGroupId(groupId);
        existingAdmins.setAdminUsers(admins.getAdminUsers());
        return repository.save(existingAdmins);
    }

    public void delete(String groupId) {
        if (!repository.existsById(groupId)) {
            throw new ObjectNotFoundException("Admins not found for group: " + groupId);
        }
        repository.deleteById(groupId);
    }

    public List<String> findGroupsWhereUserIsAdmin(String userId) {
        return repository.findAll().stream()
                .filter(admins -> admins.getAdminUsers().stream()
                        .anyMatch(user -> user.getId().equals(userId)))
                .map(Admins::getGroupId)
                .collect(Collectors.toList());
    }
}