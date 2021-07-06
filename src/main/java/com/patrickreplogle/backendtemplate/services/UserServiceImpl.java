package com.patrickreplogle.backendtemplate.services;

import com.patrickreplogle.backendtemplate.exceptions.ResourceNotFoundException;
import com.patrickreplogle.backendtemplate.models.Role;
import com.patrickreplogle.backendtemplate.models.User;
import com.patrickreplogle.backendtemplate.models.UserRoles;
import com.patrickreplogle.backendtemplate.repository.UserRepository;
import com.patrickreplogle.backendtemplate.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        userRepository.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    @Override
    public User findByName(String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username.toLowerCase());

        if (user == null) {
            throw new ResourceNotFoundException("User " + username + " not found.");
        }

        return user;
    }

    @Override
    public User findUserById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found."));
    }

    @Override
    public List<User> findUsersByZipcode(String string) {
        return userRepository.findByZipcode(string);
    }

    @Override
    public List<User> findUsersByCity(String string) {
        return userRepository.findByCityContainingIgnoreCase(string);
    }

    @Transactional
    @Override
    public User save(User user) {
        {
            User newUser = new User();

            if (user.getUserid() != 0) {
                userRepository.findById(user.getUserid())
                        .orElseThrow(() -> new EntityNotFoundException("User id " + user.getUserid() + " not found!"));
                newUser.setUserid(user.getUserid());
            }

            newUser.setUsername(user.getUsername().toLowerCase());
            newUser.setPasswordNoEncrypt(user.getPassword());
            newUser.setEmail(user.getEmail().toLowerCase());
            newUser.setFirstname(user.getFirstname());
            newUser.setLastname(user.getLastname());
            newUser.setCity(user.getCity());
            newUser.setZipcode(user.getZipcode());

            newUser.getRoles().clear();

            for (UserRoles ur : user.getRoles()) {
                Role addRole = roleService.findRoleById(ur.getRole()
                        .getRoleid());
                newUser.getRoles()
                        .add(new UserRoles(newUser, addRole));
            }

            // automatically assign a new user a "user" role if they haven't been assigned one already
            if (newUser.getRoles().size() == 0) {
                Role addRole = roleService.findByName(Constants.USER_ROLE);
                newUser.getRoles().add(new UserRoles(newUser, addRole));
            }

            return userRepository.save(newUser);
        }
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found."));
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);

        if (user.getUsername() != null) {
            currentUser.setUsername(user.getUsername()
                    .toLowerCase());
        }

        if (user.getPassword() != null) {
            currentUser.setPasswordNoEncrypt(user.getPassword());
        }

        if (user.getFirstname() != null) {
            currentUser.setFirstname(user.getFirstname());
        }

        if (user.getLastname() != null) {
            currentUser.setLastname(user.getLastname());
        }

        if (user.getZipcode() != null) {
            currentUser.setZipcode(user.getZipcode());
        }

        if (user.getCity() != null) {
            currentUser.setCity(user.getCity());
        }

        return userRepository.save(currentUser);
    }


}
