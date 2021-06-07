package com.patrickreplogle.backendtemplate.services;

import com.patrickreplogle.backendtemplate.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findByNameContaining(String username);

    List<User> findUsersByZipcode(String string);

    List<User> findUsersByCity(String string);

    User findByName(String username);

    User findUserById(long id);

    User save(User user);

    void delete(long id);

    User update(User user, long id);

}
