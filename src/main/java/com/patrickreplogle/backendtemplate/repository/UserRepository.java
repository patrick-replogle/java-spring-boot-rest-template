package com.patrickreplogle.backendtemplate.repository;

import com.patrickreplogle.backendtemplate.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByZipcode(String zipcode);

    List<User> findByCityContainingIgnoreCase(String city);
}
