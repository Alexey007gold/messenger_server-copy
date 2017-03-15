package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.TokenCreationTimeEntity;
import com.alexkoveckiy.common.dao.repositories.TokenCreationTimeRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 12.03.17.
 */
@Component
public class TokenCreationTimeService extends BaseService<TokenCreationTimeEntity, TokenCreationTimeRepository, String> {
}
