package org.medx.elixrlabs.service.impl;

import org.medx.elixrlabs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.model.User;

/**
 * <p>
 * Service implementation for managing User-related operations.
 * This class contains business logic for handling User operations. It acts as
 * a bridge between the controller layer and the repository layer, ensuring that
 * business rules are applied before interacting with the database.
 * </p>
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailWithRoles(email);
        if (null != user) {
            return user;
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
