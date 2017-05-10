package com.rodrigobresan.mockitolearning.model;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public class UserRegistration {

    private Database database;

    public UserRegistration(Database database) {
        this.database = database;
    }

    public void registerNewUser(String emailAddress) throws UserAlreadyRegisteredException {
        if (database.hasUser(emailAddress)) {
            throw new UserAlreadyRegisteredException();
        }

        database.addUser(emailAddress);
    }
}
