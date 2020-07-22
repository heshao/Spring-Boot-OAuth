package com.example.auth.dao;

import com.example.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author heshao
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);
}
