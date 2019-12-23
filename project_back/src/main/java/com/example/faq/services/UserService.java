package com.example.faq.services;

import com.example.faq.models.User;
import com.example.faq.persistences.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findUserById(Integer id) {
        User user = userDao.findByUserId(id);
        return user;
    }

    public User findUserByUsername(String username) {
        User user = userDao.findByUserName(username);
        return user;
    }

    public Integer addUser(User username) {
        return userDao.userResgister(username);
    }

    public List<User> findUsers() {
        return userDao.getUserdropDownlist();
    }

    public Integer editPwd(User user) {
        return userDao.updatePassword(user);
    }

    public User findUserByUserRealname(String realname) {
        return userDao.findUserByUserRealname(realname);
    }
}
