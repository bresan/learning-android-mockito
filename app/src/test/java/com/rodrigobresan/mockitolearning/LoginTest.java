package com.rodrigobresan.mockitolearning;

import com.rodrigobresan.mockitolearning.model.Database;
import com.rodrigobresan.mockitolearning.model.UserAlreadyRegisteredException;
import com.rodrigobresan.mockitolearning.model.UserRegistration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public class LoginTest {

    private Database mockedDatabase;

    @Before
    public void setup() {
        mockedDatabase = Mockito.mock(Database.class);
    }

    @Test(expected = UserAlreadyRegisteredException.class)
    public void shouldThrowUserAlreadyRegisteredException() throws UserAlreadyRegisteredException {
        UserRegistration userRegistration = new UserRegistration(mockedDatabase);

        when(mockedDatabase.hasUser(anyString())).thenReturn(true);

        userRegistration.registerNewUser("rodrigo.bresan@email.com");
    }
}
