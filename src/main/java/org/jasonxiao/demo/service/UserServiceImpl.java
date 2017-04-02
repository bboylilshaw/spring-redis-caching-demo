package org.jasonxiao.demo.service;

import org.jasonxiao.demo.model.User;
import org.jasonxiao.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Jason Xiao
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(cacheNames = "users", key = "'all.users'")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "users", key = "#id")
    public User getUser(Long id) {
        logger.info("Retrieve user from db");
        return userRepository.findOne(id);
    }

    @Override
    @CacheEvict(cacheNames = "users", key = "'all.users'")
//    @Transactional(propagation = Propagation.REQUIRES_NEW) // even this is set as REQUIRES_NEW, if outer tx fail, cache change will not apply
    public User addUser(User user) {
        logger.info("Save user to db");
        return userRepository.save(user);
    }

    @Override
    @CachePut(cacheNames = "users", key = "#id")
    @CacheEvict(cacheNames = "users", key = "'all.users'")
    public User updateUser(Long id, User user) {
        Assert.notNull(id, "id cannot be null");
        User toBeUpdated = userRepository.getOne(id);
        BeanUtils.copyProperties(user, toBeUpdated, "id");

        if (id == 2) throw new RuntimeException("SOME ERROR!!!");
        return userRepository.save(toBeUpdated);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "users", key = "#id"),
            @CacheEvict(cacheNames = "users", key = "'all.users'"),
    })
    public void deleteUser(Long id) {
        logger.info("Deleting user from db");
        userRepository.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void evictCache() {
        logger.info("Evicted all user cache!");
    }
}
