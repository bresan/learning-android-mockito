package com.rodrigobresan.mockitolearning.model;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public class UserRegistration {

    private Database database;
    private EmailSender emailSender;

    public UserRegistration(Database database, EmailSender emailSender) {
        this.database = database;
        this.emailSender = emailSender;
    }

    public void registerNewUser(String emailAddress) throws UserAlreadyRegisteredException, EmailFailedException {
        if (database.hasUser(emailAddress)) {
            throw new UserAlreadyRegisteredException();
        }

        if (!emailSender.sendRegistrationEmail(new RegistrationEmail(emailAddress))) {
            throw new EmailFailedException();
        }

        database.addUser(emailAddress);
    }
}
