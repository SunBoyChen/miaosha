package com.hua.miaosha.service.impl;

import com.hua.miaosha.dao.UserMapper;
import com.hua.miaosha.domain.User;
import com.hua.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public Boolean insert(User user) {
        return  userMapper.insert(user)>0?true:false;
    }

    @Override
    @Transactional
    public Boolean tx() {

        User user = new User();
        user.setId(7);
        user.setAge(26);
        user.setBalance(new BigDecimal(100));
        user.setName("zhi");
        user.setUsername("user1");
        userMapper.insert(user);

        User user2 = new User();
        user2.setId(5);
        user2.setAge(27);
        user2.setBalance(new BigDecimal(100));
        user2.setName("hua");
        user2.setUsername("user2");
        userMapper.insert(user2);

        return true;
    }
}
