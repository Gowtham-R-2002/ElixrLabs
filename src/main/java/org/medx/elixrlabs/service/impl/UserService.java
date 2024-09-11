package org.medx.elixrlabs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.medx.elixrlabs.exception.LabException;
import org.medx.elixrlabs.model.User;
import org.medx.elixrlabs.repository.UserRepository;

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

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Attempting to load user by email: {}", email);
        User user;
        try {
            user = userRepository.findByEmailWithRole(email);
        } catch (UsernameNotFoundException e) {
            logger.warn("User not found with email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        } catch (Exception e) {
            logger.error("Error occurred while fetching user with email : {}" , email );
            throw new LabException("Error occurred while fetching user with email :" + email );
        }
        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
