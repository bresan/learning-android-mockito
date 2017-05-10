package com.rodrigobresan.mockitolearning;

import com.rodrigobresan.mockitolearning.model.Database;
import com.rodrigobresan.mockitolearning.model.EmailFailedException;
import com.rodrigobresan.mockitolearning.model.EmailSender;
import com.rodrigobresan.mockitolearning.model.UserAlreadyRegisteredException;
import com.rodrigobresan.mockitolearning.model.UserRegistration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public class LoginTest {

    private Database mockedDatabase;
    private UserRegistration userRegistration;
    private EmailSender emailSender;

    @Before
    public void setup() {
        mockedDatabase = Mockito.mock(Database.class);
        emailSender = Mockito.mock(EmailSender.class);

        userRegistration = new UserRegistration(mockedDatabase, emailSender);
    }

    @Test(expected = UserAlreadyRegisteredException.class)
    public void shouldThrowUserAlreadyRegisteredException() throws UserAlreadyRegisteredException, EmailFailedException {
        when(mockedDatabase.hasUser(anyString())).thenReturn(true);
        userRegistration.registerNewUser("rodrigo.bresan@email.com");
    }

    @Test
    public void shouldAddNewUserToDatabase() throws UserAlreadyRegisteredException, EmailFailedException {
        String email = "rodrigo.bresan@email.com";

        // now that our mocked database will return false, it will follow until the end and
        // add the user
        when(mockedDatabase.hasUser(email)).thenReturn(false);

        userRegistration.registerNewUser(email);

        // check if our addUser method on our mocked database was called
        verify(mockedDatabase).addUser(email);
    }
}
