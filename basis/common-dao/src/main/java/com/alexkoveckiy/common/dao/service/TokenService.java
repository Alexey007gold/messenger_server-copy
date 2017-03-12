package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.TokenEntity;
import com.alexkoveckiy.common.dao.repositories.TokenRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 12.03.17.
 */
@Component
public class TokenService extends BaseService<TokenRepository, TokenEntity> {
}
