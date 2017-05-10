package com.rodrigobresan.mockitolearning.model;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public class RegistrationEmail {
    private String destinationEmail;

    public RegistrationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }

    public String getDestinationEmail() {
        return destinationEmail;
    }

    public void setDestinationEmail(String destinationEmail) {
        this.destinationEmail = destinationEmail;
    }
}
