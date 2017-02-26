package com.softgroup.common.router;

import org.jose4j.keys.AesKey;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class TokenKey {
    public final AesKey aesKey = new AesKey("superKeyyeKrepus".getBytes());
}
