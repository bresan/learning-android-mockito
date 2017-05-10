package com.rodrigobresan.mockitolearning.model;

import java.util.List;

/**
 * Created by rodrigobresan on 5/10/17.
 * <p>
 * E-mail: rcbresan@gmail.com
 * Github: bresan
 */

public interface Database {
    void addUser(String emailAddress);

    boolean hasUser(String emailAddress);

    void deleteUser(String emailAddress);

    int numberOfUsers();

    boolean isReadWriteSupported();

    String getDatabaseName();

    List<String> getUsers();
}
