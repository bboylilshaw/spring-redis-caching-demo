package org.jasonxiao.demo.service;

import org.jasonxiao.demo.model.User;

import java.util.List;

/**
 * @author Jason Xiao
 */
public interface UserService {

    List<User> getAllUsers();

    User getUser(Long id);

    User addUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    void evictCache();

}
