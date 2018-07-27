package com.sell.sell.utils;

import java.util.Random;

public class KeyUtil {

    // format: time + random number
    public static synchronized String genUniqueKey(){
        Random random = new Random();

        Integer number = random.nextInt(9000000) + 1000000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
