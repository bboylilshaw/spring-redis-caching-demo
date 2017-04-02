package org.jasonxiao.demo.service;

import org.jasonxiao.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jason Xiao
 */
@Service
@Transactional
public class FakeService {

    private final UserService userService;

    public FakeService(UserService userService) {
        this.userService = userService;
    }

    public void doWorkSuccess() {
        User user = new User();
        user.setName("Jason");
        userService.addUser(user);
    }

    public void doWorkFail() {
        User user = new User();
        user.setName("Jason");
        userService.addUser(user);
        throw new RuntimeException("Run into some error");
    }

}
