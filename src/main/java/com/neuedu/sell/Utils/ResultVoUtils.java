package com.neuedu.sell.Utils;

import com.neuedu.sell.vo.ResultVo;

public class ResultVoUtils {

    public static ResultVo success(Object data){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(data);
        return resultVo;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
