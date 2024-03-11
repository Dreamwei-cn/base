package com.dream.spi.log;

/**
 * @author 思维穿梭
 */
public interface Logger {
    /**
     *  info 级别打印日志
     * @param message
     */
    void info(String message);

    /**
     *  debug 级别打印日志
     * @param message
     */
    void debug(String message);

}
