package com.dream.spi;

import com.dream.spi.log.LoggerService;

public class Main {
    public static void main(String[] args) {
        LoggerService service = LoggerService.getService();
        service.info("Hello, JAVA SPI!");
        service.debug("Hello, JAVA SPI!");
    }
}
