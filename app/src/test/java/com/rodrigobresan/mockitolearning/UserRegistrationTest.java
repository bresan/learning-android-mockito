package com.rodrigobresan.mockitolearning;

import com.rodrigobresan.mockitolearning.model.Database;
import exception.EmailFailedException;
import com.rodrigobresan.mockitolearning.model.EmailSender;
import com.rodrigobresan.mockitolearning.model.RegistrationEmail;
import exception.UserAlreadyRegisteredException;
import com.rodrigobresan.mockitolearning.model.UserRegistration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 * <p>
 * This class is used to describe a simple unit test using the Mockito test framework. In this
 * example it will be shown how to mock a class responsible for database access (in our case, it
 * will be called mockedDatabase) in order to test a simple registration flow in our application.
 * <p>
 * We will also be mocking the class EmailSender in this example.
 */

public class UserRegistrationTest {

    private static final String SAMPLE_REGISTRATION_EMAIL = "rodrigo.bresan@email.com";

    // We could also use the @Mock annotation here in order to mock this object
    private Database mockedDatabase;
    private EmailSender emailSender;

    private UserRegistration userRegistration;

    /**
     * This method will be called before all our tests. Here we will mock all the dependencies we
     * need in order to run our tests.
     */
    @Before
    public void setup() {
        mockedDatabase = Mockito.mock(Database.class);
        emailSender = Mockito.mock(EmailSender.class);

        userRegistration = new UserRegistration(mockedDatabase, emailSender);
    }

    /**
     * In this test we are expecting to receive a exception of type UserAlreadyRegisteredException,
     * as you can see with the property expected inside the @Test annotation
     *
     * @throws UserAlreadyRegisteredException Exception of already registered user
     * @throws EmailFailedException           Exception in case the email of registration fails
     *                                        to be sent
     */
    @Test(expected = UserAlreadyRegisteredException.class)
    public void shouldThrowUserAlreadyRegisteredException() throws UserAlreadyRegisteredException,
            EmailFailedException {
        when(mockedDatabase.hasUser(anyString())).thenReturn(true);
        userRegistration.registerNewUser(SAMPLE_REGISTRATION_EMAIL);
    }

    /**
     * This method will do the test to add a new user into our database. It will force our email
     * sender mock to return true in order to simulate the flow of registering user into the
     * database.
     *
     * @throws UserAlreadyRegisteredException Exception for when user already is registered
     * @throws EmailFailedException           Exception for when registration email fails to be sent
     */
    @Test
    public void shouldAddNewUserToDatabase() throws UserAlreadyRegisteredException, EmailFailedException {

        // Here we force our mocked database to return the boolean value true when the method
        // hasUser(String) is called with the defined parameter (SAMPLE_REGISTRATION_EMAIL)
        when(mockedDatabase.hasUser(SAMPLE_REGISTRATION_EMAIL)).thenReturn(false);

        // and also force our email sender to behave in the same way
        when(emailSender.sendRegistrationEmail(any(RegistrationEmail.class))).thenReturn(true);

        // call the method to really register the user
        userRegistration.registerNewUser(SAMPLE_REGISTRATION_EMAIL);

        // and finally do a verification with our database object to see if the user was properly
        // registered into the database
        verify(mockedDatabase).addUser(SAMPLE_REGISTRATION_EMAIL);
    }
}
