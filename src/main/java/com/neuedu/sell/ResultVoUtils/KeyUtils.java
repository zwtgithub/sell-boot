package com.neuedu.sell.ResultVoUtils;

import java.util.Random;

public class KeyUtils {

    /**
     * 时间+随机六位数
     * @return
     */
    public static synchronized String generateUniqueKey(){
        Random random=new Random();
        int number=random.nextInt(900000)+100000;
        return String.valueOf(System.currentTimeMillis())+number;
    }
}
