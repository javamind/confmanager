package com.ninjamind.confman.service;

import com.ninjamind.confman.domain.User;
import com.ninjamind.confman.repository.UserRepository;

/**
 * Service class for managing users.
 */
public interface UserService extends GenericFacade<User, String, UserRepository> {

    /**
     * Create user
     * @param login
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param langKey
     * @return
     */
    User createUserInformation(String login, String password, String firstName, String lastName, String email,
                                      String langKey);


    /**
     * Update user
     * @param firstName
     * @param lastName
     * @param email
     */
    void updateUserInformation(String firstName, String lastName, String email);

    /**
     *
     * @param password
     */
    void changePassword(String password);

    /**
     *
     * @return
     */
    public User getUserWithAuthorities();


}
