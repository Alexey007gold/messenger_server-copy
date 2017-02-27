package com.softgroup.common.router.api;

/**
 * Created by alex on 27.02.17.
 */
public interface HandlerFactory<T> {
    Handler getHandler(String name);
}
