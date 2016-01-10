package org.jasonxiao.demo.repository;

import org.jasonxiao.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Jason on 1/10/16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
