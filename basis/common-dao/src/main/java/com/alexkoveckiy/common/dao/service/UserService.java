package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.UserEntity;
import com.alexkoveckiy.common.dao.repositories.UserRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 11.03.17.
 */

@Component
public class UserService extends BaseService<UserEntity, UserRepository> {
}
