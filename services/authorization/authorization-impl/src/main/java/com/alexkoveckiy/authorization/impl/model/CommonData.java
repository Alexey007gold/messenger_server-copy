package com.alexkoveckiy.authorization.impl.model;

import org.jose4j.keys.AesKey;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class CommonData {
    public final AesKey aesKey = new AesKey("superKeyyeKrepus".getBytes());
}
