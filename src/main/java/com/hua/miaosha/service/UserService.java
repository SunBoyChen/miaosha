package com.hua.miaosha.service;

import com.hua.miaosha.domain.User;

import java.util.List;

public interface UserService {

   User getById(int id);

   List<User> getAll();

   Boolean insert(User user);

   Boolean tx();
}
