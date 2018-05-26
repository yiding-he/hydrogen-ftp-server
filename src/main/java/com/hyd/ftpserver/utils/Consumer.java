package com.hyd.ftpserver.utils;

/**
 * @author yidin
 */
@FunctionalInterface
public interface Consumer<T> {

    void accept(T t) throws Exception;
}
