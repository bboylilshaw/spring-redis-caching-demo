package org.jasonxiao.demo.repository;

import org.jasonxiao.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jason Xiao
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
