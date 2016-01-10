package org.jasonxiao.demo.service;

import org.jasonxiao.demo.model.User;

import java.util.Collection;

/**
 * Created by Jason on 1/10/16.
 */
public interface UserService {

    Collection<User> findAll();

    User findOne(Long id);

    User create(User user);

    User update(Long id, User user);

    void delete(Long id);

    void evictCache();

}
