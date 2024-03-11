package com.dream.spi.log.provider;

import com.dream.spi.log.Logger;

/**
 *  @author 思维穿梭
 */
public class LogBack implements Logger {
    @Override
    public void info(String message) {
        System.out.println("LogBack info 打印日志： " + message);
    }

    @Override
    public void debug(String message) {
        System.out.println("LogBack debug 打印日志： " + message);
    }
}
