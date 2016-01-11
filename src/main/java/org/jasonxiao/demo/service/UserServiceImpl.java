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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Created by Jason on 1/10/16.
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Cacheable(value = "all_users")
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Cacheable(value = "user")
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User create(User user) {
        LOGGER.info("creating user");
        return userRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CachePut(value = "user", key = "#id")
    public User update(Long id, User user) {
        Assert.notNull(id);
        User toBeUpdated = userRepository.getOne(id);
        BeanUtils.copyProperties(user, toBeUpdated, "id");

        if (id == 2) throw new RuntimeException("SOME ERROR!!!");
        return userRepository.save(toBeUpdated);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @CacheEvict("user")
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = {"all_users", "user"}, allEntries = true)
    public void evictCache() {
        LOGGER.info("Evicted all cache!");
    }
}
