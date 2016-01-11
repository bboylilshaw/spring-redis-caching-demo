package org.jasonxiao.demo.web.controller;

import org.jasonxiao.demo.model.User;
import org.jasonxiao.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by Jason on 1/10/16.
 */
@RestController
public class UserRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(
            value = "/api/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Collection<User>> getAllUsers() {
        Collection<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/user",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOGGER.info("create user...");

        User savedUser = userService.create(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/api/user/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        LOGGER.info("get user...");

        User user = userService.findOne(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/user/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        LOGGER.info("update user...");

        User updatedUser = userService.update(id, user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/user/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        LOGGER.info("delete user...");

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
