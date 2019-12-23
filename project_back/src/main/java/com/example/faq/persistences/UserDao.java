package com.example.faq.persistences;

import com.example.faq.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User getUser(Integer id);

    List<User> getUserdropDownlist();

    Integer userResgister(User user);

    Integer updatePassword(User user);

    User findByUserId(Integer id);

    User findByUserName(String name);

    User findUserByUserRealname(String realname);
}
