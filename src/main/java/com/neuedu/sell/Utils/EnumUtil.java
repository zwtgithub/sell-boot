package com.neuedu.sell.Utils;

import com.neuedu.sell.enums.CodeEnum;

public class EnumUtil {

    public static <T extends CodeEnum> T getEnumByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())){
                return each;
            }
        }
       return null;
    }
}
