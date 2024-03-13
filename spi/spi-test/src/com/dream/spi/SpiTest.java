package com.dream.spi;

import com.dream.spi.Iterable.MyIterable;
import com.dream.spi.log.LoggerService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 思维穿梭
 */
public class SpiTest
{
    public static void main(String[] args)
    {
        // 这里可以调用SPI的实现类的方法
        LoggerService loggerService = LoggerService.getService();
        loggerService.info("你好");
        loggerService.debug("测试Java SPI 机制");

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");

        MyIterable<String>
            myIterable = new MyIterable<>(list);

        for (String s : myIterable) {
            System.out.println(s);
        }

    }
}
