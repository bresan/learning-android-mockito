package com.rodrigobresan.mockitolearning.model;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public interface EmailSender {
    boolean sendRegistrationEmail(RegistrationEmail registrationEmail);
}
