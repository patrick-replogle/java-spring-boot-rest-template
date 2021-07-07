package com.patrickreplogle.backendtemplate.controllers;

import com.patrickreplogle.backendtemplate.models.User;
import com.patrickreplogle.backendtemplate.services.UserService;
import com.patrickreplogle.backendtemplate.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(Constants.USERS_ENDPOINT) // "/users
public class UserController {

    @Autowired
    private UserService userService;

    // returns a list of all users
    @GetMapping(value = "/users",
            produces = "application/json")
    public ResponseEntity<?> listAllUsers() {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    // returns a single user based off a user id
    @GetMapping(value = "/user/{userId}",
            produces = "application/json")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        User u = userService.findUserById(userId);

        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // return a user object based on a given username
    @GetMapping(value = "/user/name/{userName}",
            produces = "application/json")
    public ResponseEntity<?> getUserByName(
            @PathVariable
                    String userName) {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // returns a list of users whose username contains the given substring
    @GetMapping(value = "/user/name/like/{userName}",
            produces = "application/json")
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
                    String userName) {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(u,
                HttpStatus.OK);
    }

    // returns a list users that share the provided zipcode
    @GetMapping(value = "/zipcode/{zipCode}",
                produces = "application/json")
    public ResponseEntity<?> listUsersByZipCode(@PathVariable String zipCode) {
        List<User> users = userService.findUsersByZipcode(zipCode);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // returns a list users that share the provided city
    @GetMapping(value = "/city/{city}",
            produces = "application/json")
    public ResponseEntity<?> listUsersByCity(@PathVariable String city) {
        List<User> users = userService.findUsersByCity(city);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // register a new user record
    @PostMapping(value = "/register",
            consumes = "application/json")
    public ResponseEntity<?> addNewUser(
            @Valid
            @RequestBody
                    User newuser) throws
            URISyntaxException {
        newuser.setUserid(0);
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(newuser,
                responseHeaders,
                HttpStatus.CREATED);
    }

    // update an entire user record
    @PutMapping(value = "/user/{userid}",
            consumes = "application/json")
    public ResponseEntity<?> updateFullUser(
            @Valid
            @RequestBody
                    User updateUser,
            @PathVariable
                    long userid) {
        updateUser.setUserid(userid);
        userService.save(updateUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

   // partially update a user record
    @PatchMapping(value = "/user/{id}",
            consumes = "application/json")
    public ResponseEntity<?> updateUser(
            @RequestBody
                    User updateUser,
            @PathVariable
                    long id) {
        userService.update(updateUser,
                id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // delete a user record
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
